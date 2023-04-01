from base64 import b64encode
import requests
from flask import Flask, render_template, request, redirect, make_response

app = Flask(__name__)


@app.route('/')
@app.route('/login')
def login_fct():
    return render_template('login.html')


def basic_auth(username, password):
    token = b64encode(f"{username}:{password}".encode('utf-8')).decode("ascii")
    return f'Basic {token}'


@app.route('/verify', methods=['GET','POST'])
def verify_fct():
    password =  request.form['password']
    username = request.form['username']
    headers = {'Authorization': basic_auth(username, password)}
    response = requests.get("https://localhost:8080/api/user/login",headers=headers)
    if response.status_code == 200:
        return redirect('/home')
    else:
        return make_response('Not ok', 400)


@app.route('/home', methods=['GET','POST'])
def home_fct():
    return render_template('home.html')


@app.route('/profile')
def profile_fct():

    e1 =["08:00","09:00","Busy"]
    e2 = ["09:00", "10:00", "Busy"]
    e3 = ["10:00", "11:00", "Busy"]
    e4 = ["11:00", "12:00", "Busy"]
    e5 = ["12:00", "13:00", "Busy"]
    e6 = ["13:00", "14:00", "Busy"]
    e7 = ["14:00", "15:00", "Busy"]
    e8 = ["15:00", "16:00", "Busy"]
    e9 = ["16:00", "17:00", "Busy"]
    e10 = ["17:00", "18:00", "Free"]
    elements=[e1,e2,e3,e4,e5,e6,e7,e8,e9,e10]
    return render_template('profile.html',elements=elements)


if __name__ == '__main__':
    app.run(debug=True)
