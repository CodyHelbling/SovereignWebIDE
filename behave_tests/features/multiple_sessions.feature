Feature: Opens multiple sessions

    Scenario: Runs multiple sessions test
        Given we have a web based IDE
        When we open 10 sessions
	Then all sessions will show the IDE
