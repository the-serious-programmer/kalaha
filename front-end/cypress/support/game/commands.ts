const createGame = 'create-game';

Cypress.Commands.add('createGame', () => {
  cy.interceptCreateGame();
  cy.visit('');
  cy.waitForCreateGame();
});

Cypress.Commands.add('interceptCreateGame', () => {
  cy.intercept('POST', '**/game', {fixture: 'game/create.json'}).as(createGame);
});

Cypress.Commands.add('waitForCreateGame', () => {
  cy.wait(`@${createGame}`);
});
