from flask import Flask, render_template, request, redirect

app = Flask(__name__)
@app.route('/')
@app.route('/login')
def login_fct():

    pass



if __name__ == '__main__':
    app.run(debug=True)
