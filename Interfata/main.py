import json
from base64 import b64encode
import datetime
import requests
from flask import Flask, render_template, request, redirect, make_response

app = Flask(__name__)

data = {}


@app.route('/')
@app.route('/login')
def login_fct():
    return render_template('login.html')


def basic_auth(username, password):
    token = b64encode(f"{username}:{password}".encode('utf-8')).decode("ascii")
    return f'Basic {token}'


@app.route('/verify', methods=['GET', 'POST'])
def verify_fct():
    password = request.form['password']
    username = request.form['username']
    headers = {'Authorization': basic_auth(username, password)}
    response = requests.get("http://localhost:9091/api/user/login", headers=headers)

    ldata = json.loads(response.content)
    global data
    data = ldata
    if response.status_code == 200:

        return redirect('/home')
    else:
        return make_response('Not ok', 400)


@app.route('/logout', methods=['GET', 'POST'])
def logout_fct():
    global data
    username = data['username']
    headers = {'Authorization': basic_auth(username, "")}
    response = requests.get("http://localhost:9091/api/user/logout", headers=headers)
    if response.status_code == 200:
        return redirect('/login')
    else:
        return make_response('Not ok', 400)


@app.route('/home', methods=['GET', 'POST'])
def home_fct():
    with open('recent.txt', 'r') as r:
        txt = r.read()
    info=txt.split("\n")
    return render_template('home.html', username=data['username'],info=info)


@app.route('/view', methods=['GET', 'POST'])
def view_fct():
    global data
    username = data['username']
    headers = {'Authorization': basic_auth(username, "")}
    response = requests.get("http://localhost:9091/api/break-request", headers=headers)
    breakRequestData = json.loads(response.content)
    return render_template('view.html', data=breakRequestData)


@app.route('/send', methods=['GET', 'POST'])
def send_fct():
    global data
    username = data['username']
    headers = {'Authorization': basic_auth(username, "")}
    response = requests.get(f"http://localhost:9091/api/timeline/range?startDate=2023-04-01T00:00:00Z&endDate=2023-04-01T23:59:59Z", headers=headers)
    timelines = json.loads(response.content)
    return render_template('send.html', timelines=timelines)


@app.route('/sendInvite', methods=['GET', 'POST'])
def send_invite():
    global data
    username = data['username']
    headers = {'Authorization': basic_auth(username, "")}
    place = request.form['placeInput']
    comment = request.form['commentInput']
    page_ids = request.form.getlist("participant")
    json_data = {
        "place": place,
        "comment": comment,
        "timelineUuids": page_ids
    }
    response = requests.post("http://localhost:9091/api/break-request", json=json_data, headers=headers)
    return redirect('/send')

@app.route('/search', methods=['GET', 'POST'])
def search_fct():
    username = request.form['username']
    with open('recent.txt', 'r') as r:
        data = r.read()
        elements = data.split("\n")
        if len(elements) < 5:
            if elements[0] == '':
                elements[0] = str(username)
            else:
                elements.append(str(username))
        else:
            elements[0] = username
    r.close()
    with open('recent.txt', 'w') as w:
        for e in elements:
            if e and len(e)>1:
               w.write(e + "\n")
    return redirect('/profile')


@app.route('/profile')
def profile_fct():
    global data
    username = data['username']
    headers = {'Authorization': basic_auth(username, "")}
    response = requests.get(f"http://localhost:9091/api/user/timeline", headers=headers)
    timelines = json.loads(response.content)
    e1 = ["08:00", "09:00", "Busy"]
    e2 = ["09:00", "10:00", "Busy"]
    e3 = ["10:00", "11:00", "Busy"]
    e4 = ["11:00", "12:00", "Busy"]
    e5 = ["12:00", "13:00", "Busy"]
    e6 = ["13:00", "14:00", "Busy"]
    e7 = ["14:00", "15:00", "Busy"]
    e8 = ["15:00", "16:00", "Busy"]
    e9 = ["16:00", "17:00", "Busy"]
    e10 = ["17:00", "18:00", "Busy"]
    elements = [e1, e2, e3, e4, e5, e6, e7, e8, e9, e10]
    return render_template('profile.html', elements=elements, info=data)


@app.route('/update', methods=['GET', 'POST'])
def update_fct():
    global data
    username = data['username']
    firstName = request.form['firstName']
    lastName = request.form['lastName']
    department = request.form['department']
    officeName = request.form['officeName']
    teamName = request.form['teamName']
    floorNumber = request.form['floorNumber']
    # newProfilePicName = request.files['profilePicName']

    # data=[username,firstName,lastName,department,officeName,teamName,floorNumber,'null','null']
    # jsonfile=json.dumps(data)
    jsonfile = {
        "username": username,
        "firstName": firstName,
        "lastName": lastName,
        "department": department,
        "officeName": officeName,
        "teamName": teamName,
        "floorNumber": floorNumber,
    }
    headers = {'Authorization': basic_auth(username, "")}
    response = requests.patch("http://localhost:9091/api/user/save-profile", headers=headers, json=jsonfile)

    if response.status_code == 200:

        data = json.loads(response.content)
        return redirect('/profile')
    else:
        return make_response('Not ok', 400)


if __name__ == '__main__':
    app.run(debug=True)
