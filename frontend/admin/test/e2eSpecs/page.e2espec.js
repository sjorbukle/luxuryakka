describe('home page title', function() {
    var ptor = protractor.getInstance();
    it('should be aaaa', function() {
        ptor.get('/#');
        expect(ptor.getTitle()).toBe('aaaa');
    });
});
