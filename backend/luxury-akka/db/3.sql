CREATE TABLE public.organization_structure_type
(
  id    BIGSERIAL PRIMARY KEY NOT NULL,
  name  VARCHAR(80)  NOT NULL
);

CREATE TABLE public.organization_structure
(
  id                BIGSERIAL PRIMARY KEY NOT NULL,
  name              VARCHAR(80)  NOT NULL,
  parent_id         BIGINT,
  entity_type_id    BIGINT  NOT NULL,
  description       TEXT,
  short_description TEXT,

  FOREIGN KEY (parent_id) REFERENCES organization_structure(id),
  FOREIGN KEY (entity_type_id) REFERENCES organization_structure_type(id)
);