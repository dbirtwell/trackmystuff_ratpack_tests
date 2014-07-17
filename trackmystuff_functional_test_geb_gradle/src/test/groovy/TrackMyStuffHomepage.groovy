import geb.*

class TrackMyStuffHomepage extends Page {
    static url = "http://localhost:5050"
    static at = { title == "Track My Stuff" }
}