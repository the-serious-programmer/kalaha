describe('PitComponent', () => {
  beforeEach(() => {
    cy.createGame();
  });

  it('should have disabled score pits', () => {
    cy.get('[data-cy=score-pit]').should('be.disabled');
  });

  it('should take turn', () => {
    cy.takeTurn();
  });
});
