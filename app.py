from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/books')
def get_books():
    return jsonify([
        {'title': 'The Alchemist'},
        {'title': 'Atomic Habits'},
        {'title': 'Sapiens'},
        {'title': 'The Pragmatic Programmer'}
    ])

@app.route('/ipl-teams')
def get_ipl_teams():
    return jsonify([
        {'name': 'Chennai Super Kings'},
        {'name': 'Mumbai Indians'},
        {'name': 'Royal Challengers Bangalore'},
        {'name': 'Kolkata Knight Riders'},
        {'name': 'Delhi Capitals'},
        {'name': 'Punjab Kings'},
        {'name': 'Sunrisers Hyderabad'},
        {'name': 'Lucknow Super Giants'},
        {'name': 'Rajasthan Royals'},
        {'name': 'Gujarat Titans'}
    ])

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)