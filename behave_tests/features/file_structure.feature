Feature: Test the file structure

    Scenario: Run simple file structure test
        Given we have a web based IDE
        When we create a new file
        Then a new file has been added to the project
