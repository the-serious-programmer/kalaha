Feature: game endpoint

    Scenario: should create game
        When user requests a new game
        Then should have response status 201

    Scenario: should take turn
        When player takes turn by selecting game, player and pit
        Then should have response status 200

        When player takes turn by selecting not existing game "4a37d4b1-5fbc-429c-82f6-5aac4b7f15e5", player "4a37d4b1-5fbc-429c-82f6-5aac4b7f15e5" and pit "4a37d4b1-5fbc-429c-82f6-5aac4b7f15e5"
        Then should have response status 500

