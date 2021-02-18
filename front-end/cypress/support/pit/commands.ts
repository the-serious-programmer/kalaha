const takeTurn = 'take-turn';

Cypress.Commands.add('takeTurn', () => {
  cy.interceptTakeTurn();
  cy.get('[data-cy=regular-pit]').first().click();
  cy.waitForTakeTurn();
});

Cypress.Commands.add('interceptTakeTurn', () => {
  cy.intercept('PUT', '**/game/**/player/**/pit/**', {fixture: 'pit/take-turn.json'}).as(
    takeTurn
  );
});

Cypress.Commands.add('waitForTakeTurn', () => {
  cy.wait(`@${takeTurn}`);
});
