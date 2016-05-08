from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains

@given('we have a web based IDE')
def web_ide(context):
    context.driver = webdriver.Firefox()
    context.driver.get("http://localhost:4568/index.html")

@given('we have a landing page')
def landing_page(context):
    context.driver = webdriver.Firefox()
    context.driver.get("http://localhost:4568")

@given('we type in email, username, and password')
def sign_up(context):
    context.email = context.driver.find_element_by_id("inputEmailSignUp")
    context.email.send_keys("harr5275@vandals.uidaho.edu")
    context.username = context.driver.find_element_by_id("inputUNameSignUp")
    context.username.send_keys("harr5275")
    context.password = context.driver.find_element_by_id("inputPasswordSignUp")
    context.password.send_keys("12345")

@given('we type something into the editor')
def text_input(context):
    code_mirror_element = context.driver.find_element_by_css_selector("div.CodeMirror.CodeMirror-focused > div > textarea")
    #context.driver.execute_script("arguments[0].CodeMirror-lines.setValue(arguments[1]);", code_mirror_element, "test")
    context.editor.send_keys("my name is dillon")

@when('we open 10 sessions')
def multiple_sessions(context):
    context.driver1 = webdriver.Firefox()
    context.driver2 = webdriver.Firefox()
    context.driver3 = webdriver.Firefox()
    context.driver4 = webdriver.Firefox()
    context.driver5 = webdriver.Firefox()
    context.driver6 = webdriver.Firefox()
    context.driver7 = webdriver.Firefox()
    context.driver8 = webdriver.Firefox()
    context.driver9 = webdriver.Firefox()
    context.driver10 = webdriver.Firefox()
    context.driver1.get("http://localhost:4568i/index.html")
    context.driver2.get("http://localhost:4568/index.html")
    context.driver3.get("http://localhost:4568/index.html")
    context.driver4.get("http://localhost:4568/index.html")
    context.driver5.get("http://localhost:4568/index.html")
    context.driver6.get("http://localhost:4568/index.html")
    context.driver7.get("http://localhost:4568/index.html")
    context.driver8.get("http://localhost:4568/index.html")
    context.driver9.get("http://localhost:4568/index.html")

@when('we click signup')
def click_signup_button(context):
    action = ActionChains(context.driver)
    action.click("SignUpButton")
    

@when('we create a new file')
def new_file(context):
    actions = ActionChains(context.driver)
    actions.click("drop1")

@when('we save the file')
def save_file(context):
    actions = ActionChains(context.driver)
    context.save_file = context.driver.find_element_by_id("saveFileName")
    context.save_file.send_keys("newfile")
    actions.click("btn btn-primary")

@then('all sessions will show the IDE')
def check_sessions(context):
    pass

@then('the user can sign in')
def check_user(context):
    context.userinput = context.driver.find_element_by_id("inputUser")
    context.userinput.send_keys("harr5275")
    context.pwinput = context.driver.find_element_by_id("inputPassword")
    context.pwinput.send_keys("12345")
#    context.signup_button = context.driver.find_element_by_class_name("btn btn-lg btn-primary btn-block")
#    context.signup_button.click()
    context.ide_button = context.driver.find_elements_by_class_name("a.btn.btn-success")
    print(context.ide_button)

@then('the file can be opened')
def open_file(context):
    pass 
