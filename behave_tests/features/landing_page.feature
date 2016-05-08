Feature: Test the landing page

    Scenario: Run a simple test on the landing page
        Given we have a landing page
	And we type in email, username, and password
	When we click signup
	Then the user can sign in
