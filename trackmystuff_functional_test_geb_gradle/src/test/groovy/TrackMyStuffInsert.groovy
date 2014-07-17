import geb.*

class TrackMyStuffInsert extends Page {
    static url = "http://localhost:5050/new"
    static at = { title == "Track My Stuff" }
}