Feature: Test file editor accepts different strings

    Scenario: Run a simple editor test
        Given we have a web based IDE
        And we type something into the editor
	When we save the file
	Then the file can be opened
