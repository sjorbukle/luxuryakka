package controllers.api.v1

import akka.actor.ActorRef
import com.laplacian.luxuryakka.configuration.actor.ActorFactory
import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.response.ResponseTools
import com.laplacian.luxuryakka.core.utils.StringUtils
import com.laplacian.luxuryakka.module.authentication.service.AuthenticationService
import com.laplacian.luxuryakka.module.log.action.actor.ActionLogCreateMsg
import com.laplacian.luxuryakka.module.log.action.domain.{ActionType, ActionDomainType, ActionLogEntity}
import com.laplacian.luxuryakka.module.organizationstructure.domain.{OrganizationStructureUpdateEntity, OrganizationStructureType, OrganizationStructureDetailsEntity, OrganizationStructureCreateEntity}
import com.laplacian.luxuryakka.module.organizationstructure.service.domain.OrganizationStructureDomainService
import com.laplacian.luxuryakka.module.organizationstructure.validation.{OrganizationStructureUpdateValidator, OrganizationStructureCreateValidator}
import controllers.core.SecuredController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype
import play.api.libs.json.{JsObject, JsString}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@stereotype.Controller
class OrganizationStructureController @Autowired
(
  private val organizationStructureDomainService  : OrganizationStructureDomainService,
  private val organizationStructureCreateValidator: OrganizationStructureCreateValidator,
  private val organizationStructureUpdateValidator: OrganizationStructureUpdateValidator
)
(
  implicit private val authenticationService: AuthenticationService
) extends SecuredController
{
  Asserts.argumentIsNotNull(organizationStructureDomainService)
  Asserts.argumentIsNotNull(organizationStructureCreateValidator)
  Asserts.argumentIsNotNull(organizationStructureUpdateValidator)
  Asserts.argumentIsNotNull(authenticationService)

  def read(id: Long) = AuthenticatedAction {
    request =>
      val itemCandidate = this.organizationStructureDomainService.tryGetById(id)
      if(!itemCandidate.isDefined) {
        Future(NotFound(ResponseTools.errorToRestResponse("OrganizationStructure with this id does not exist.").json))
      }
      else {
        Future(Ok(ResponseTools.data(itemCandidate).json))
      }
  }

  def create = MutateJsonAuthenticatedAction[OrganizationStructureCreateEntity](organizationStructureCreateValidator) {
    (request, validationResult, requestUser) =>
      val generatedId = this.organizationStructureDomainService.create(validationResult.validatedItem)
      val createdItem = this.organizationStructureDomainService.getById(generatedId.id)

      val itemCreatedAction = ActionLogEntity.of[OrganizationStructureDetailsEntity, OrganizationStructureDetailsEntity](
        userId      = requestUser.id,
        domainType  = ActionDomainType.ORGANIZATION_STRUCTURE,
        domainId    = createdItem.id,
        actionType  = ActionType.CREATED,
        before      = None,
        after       = Some(createdItem)
      )
      ActorFactory.actionLogActorRouter.tell(ActionLogCreateMsg(itemCreatedAction), ActorRef.noSender)

      Future.successful(Ok(ResponseTools.of(createdItem, Some(validationResult.messages)).json))
  }

  def update = MutateJsonAuthenticatedAction[OrganizationStructureUpdateEntity](organizationStructureUpdateValidator) {
    (request, validationResult, requestUser) =>

      val itemBeforeUpdate = this.organizationStructureDomainService.getById(validationResult.validatedItem.id)
      val itemAfterUpdate = this.organizationStructureDomainService.update(validationResult.validatedItem)

      val itemUpdatedAction = ActionLogEntity.of[OrganizationStructureDetailsEntity, OrganizationStructureDetailsEntity](
        userId      = requestUser.id,
        domainType  = ActionDomainType.ORGANIZATION_STRUCTURE,
        domainId    = itemAfterUpdate.id,
        actionType  = ActionType.UPDATED,
        before      = Some(itemBeforeUpdate),
        after       = Some(itemAfterUpdate)
      )
      ActorFactory.actionLogActorRouter.tell(ActionLogCreateMsg(itemUpdatedAction), ActorRef.noSender)

      Future.successful(Ok(ResponseTools.of(itemAfterUpdate, Some(validationResult.messages)).json))
  }

  def all = AuthenticatedAction {
    request =>
      val result = this.organizationStructureDomainService.getAll

      Future(Ok(ResponseTools.data(result).json))
  }

  def allByType(organizationStructureType: String) = AuthenticatedAction {
    request =>
      val organizationStructureTypeCandidate = organizationStructureType.toUpperCase
      if(StringUtils.canParseEnum[OrganizationStructureType](organizationStructureTypeCandidate)) {
        val organizationStructureType = StringUtils.parseEnum[OrganizationStructureType](organizationStructureTypeCandidate)
        val result = this.organizationStructureDomainService.getAllByType(organizationStructureType)

        Future(Ok(ResponseTools.data(result).json))
      }
      else {
        Future(BadRequest(ResponseTools.errorToRestResponse("Invalid organizationStructureType value.").json))
      }
  }

  def allLookupByType(organizationStructureType: String) = AuthenticatedAction {
    request =>
      val organizationStructureTypeCandidate = organizationStructureType.toUpperCase
      if(StringUtils.canParseEnum[OrganizationStructureType](organizationStructureTypeCandidate)) {
        val organizationStructureType = StringUtils.parseEnum[OrganizationStructureType](organizationStructureTypeCandidate)
        val result = this.organizationStructureDomainService.getAllLookupByType(organizationStructureType)

        Future(Ok(ResponseTools.data(result).json))
      }
      else {
        Future(BadRequest(ResponseTools.errorToRestResponse("Invalid organizationStructureType value.").json))
      }
  }

  def allParentLess = AuthenticatedAction {
    request =>
      val result = this.organizationStructureDomainService.getAllByType(OrganizationStructureType.COUNTRY)

      Future(Ok(ResponseTools.data(result).json))
  }

  def allByParent(parentId: Long) = AuthenticatedAction {
    request =>
      val parentCandidate = this.organizationStructureDomainService.tryGetById(parentId)
      if(parentCandidate.isDefined) {
        val result = this.organizationStructureDomainService.getAllByParent(parentCandidate.get.id)

        Future(Ok(ResponseTools.data(result).json))
      }
      else {
        Future(BadRequest(ResponseTools.errorToRestResponse("Invalid parentId value. There is not parent with received id").json))
      }
  }

  def allTypes = AuthenticatedAction {
    request =>
      val result = OrganizationStructureType.values.map(
        v => JsObject(
          Seq(
            "name"    -> JsString(v.name),
            "parent"  -> JsString(v.parentType().map(_.name).getOrElse(StringUtils.EMPTY_STRING))
          )
        )
      )

      Future(Ok(ResponseTools.data(result).json))
  }
}
