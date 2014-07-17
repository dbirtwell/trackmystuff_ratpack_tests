import geb.*
import geb.junit4.*
import org.junit.Before
import org.junit.After
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4)
class TrackMyStuffTest extends GebReportingTest {


    @Before
    void setUp() {
        

    }

    @After
    void tearDown() {
		
        // ------------------------------------
        // Delete what we just created
        // ------------------------------------

        // Walk the table until we find the row we want
        // and click the delete anchor
        def table = $(".gridtable>tbody>tr")
        table.each() { row ->
	        // get thw 7th column's anchor, the delete href
	        row.find("td", 6).find("a").click()
            // Now select the form and click the "Yes. Delete It" button
            def deleteForm = $("html>body>form")
            // The submit is the 2nd input
            deleteForm.find("input", 1).click()					
        }
		
    }

    @Test
    void trackMyStuffHomepage() {
        to TrackMyStuffHomepage
		
		// Verify the page title
        assert title == "Track My Stuff"

        // Verify the h1
        // #header>h1
        // Track My Stuff
        assert $("#header>h1").text() == "Track My Stuff"
		
		def header = $("#header")
        assert header.find("p", 1).text() == 
		  "A demo CRUD application written using Groovy and Ratpack."
		  
        // ------------------------------------------------
        // Click the "new" href and cancel from that page
        // ------------------------------------------------

        // CSS selector for "new" href and click
        // html>body>p>a
        def hrefNew = $("html>body>p>a")
        hrefNew.click()

        // Click the Cancel Button
        // html>body>a
        def hrefCancel = $("html>body>a")
        hrefCancel.click()		  

    }
	
    @Test	
    void trackMyStuffCreate() {
        to TrackMyStuffHomepage
		
        // Click the "new" Href
        $("html>body>p>a").click()		
		
		// Verify the page title
        assert title == "Track My Stuff"

        // Verify the h1
        assert $("html>body>h1").text() == "New Item Form"
		
        // ------------------------------------------------
        // Create new data
        // ------------------------------------------------

        // Select and set the form inputs
        $("#name").value("The Book of Geb")
        $("#item_type").value("PDF")
        $("#item_location_1").value("Drive 2")
        $("#item_location_2").value("Ebook Directory 1")
        $("#description").value("Just a fake book")

        //
        // Click the Form's submit button
        //
        // NOTE: This is a little tougher than most forms, as it does not
        // have an ID or NAME, so we need to use the Css selector to get the
        // form
        //
        // CSS selector to form:
        //
        // html>body>form
        // Html
        //<body>
        //  <h1>New Item Form</h1>
        //  <p>Please enter information about the new item.</p>
        //  <form accept-charset="utf-8" method="post" action="/submit">
        //    <label for="name">Name:</label>
        //    <input id="name" type="text" name="name"/>
        //    <br/>
        //    <br/>
        //  o
        //  o
        //  o
        //  </form>  

        def newForm = $("html>body>form")
        $(type: "submit").click()	

        // ------------------------------------------------
        // Verify the new data is there:
        // ------------------------------------------------

        // CSS Selector for table:
        // .gridtable>tbody>tr

        // def table = browser.$(".gridtable>tbody>tr")
        // println "TABLE has this number of elments: " + table.size()
        // println "TABLE: " + table.text()

        // Traverse the table rows, looking for one with the book title we added
        // And then verify the contents
		// TODO: Is there a better way to do this using selectors?
        def foundARow = 0
        def table = $(".gridtable>tbody>tr")
        table.each() { row ->
          if(row.text().contains("The Book of Geb")) {
	
	        // verify the td column's data is what we expect
	        assert row.find("td", 1).text() == "The Book of Geb"
	        assert row.find("td", 2).text() == "PDF"
	        assert row.find("td", 3).text() == "Drive 2"
	        assert row.find("td", 4).text() == "Ebook Directory 1"
	
	        foundARow = 1
          }
        }
        // use a "flag" to determine we actually found the item we added
        assert foundARow == 1		
    }
	
    @Test	
    void trackMyStuffUpdate() {
        to TrackMyStuffHomepage

        // CSS selector for "new" href and click
        $("html>body>p>a").click()
		
        // ------------------------------------------------
        // Create new data, so that we have something to delete
        // ------------------------------------------------

        // Select and set the form inputs
        $("#name").value("Gradle In Action")
        $("#item_type").value("eBook")
        $("#item_location_1").value("Drive 3")
        $("#item_location_2").value("Gradle Folder")
        $("#description").value("Gradle is Good!")

        // Click the Form's submit button
        def newForm = $("html>body>form")
        $(type: "submit").click()	

        // ------------------------------------------------
        // Verify the new data is there:
        // ------------------------------------------------

        // Traverse the table rows, looking for one with the book title we added
        // And then verify the contents
		// TODO: Is there a better way to do this using selectors?
        def foundARow = 0
        def table = $(".gridtable>tbody>tr")
        table.each() { row ->
          if(row.text().contains("Gradle In Action")) {
	
	        // verify the td column's data is what we expect
	        assert row.find("td", 1).text() == "Gradle In Action"
	        assert row.find("td", 2).text() == "eBook"
	        assert row.find("td", 3).text() == "Drive 3"
	        assert row.find("td", 4).text() == "Gradle Folder"
	
	        foundARow = 1
          }
        }
        // use a "flag" to determine we actually found the item we added
        assert foundARow == 1

        // ------------------------------------
        // Update what we just created
        // ------------------------------------
        foundARow = 0

        // Walk the table until we find the row we want
        // and click the update anchor
        table = $(".gridtable>tbody>tr")
        table.each() { row ->

          // We want the one we created
          if(row.text().contains("Gradle In Action")) {

            // DEBUG: Walk all the columns of the TD row  
            //def tdRow = row.find("td")
            //tdRow.each() { tdItem ->
            //  println "GOT A TD HERE: " + tdItem.text()
       	    //}
	
	        // get thw 6th column's anchor, the update href
		    foundARow = 1			
	        row.find("td", 5).find("a").click()
          }
        }
	   
        // use a "flag" to determine we actually found the item we added
        assert foundARow == 1	
		
        // Verify we are on the correct page
        assert $("html>body>h1").text() == "Edit Existing Item Form"					
        assert $("html>body>p").text() == "Please edit any information below for the item."
		
        $("#name").value("**REVISED** Gradle In Action")		
		
        // Click the Form's submit button
        def updateForm = $("html>body>form")
        $(type: "submit").click()			
		
        // ------------------------------------------------
        // Verify the new data is there:
        // ------------------------------------------------

        // Traverse the table rows, looking for one with the book title we added
        // And then verify the contents
		// TODO: Is there a better way to do this using selectors?
        foundARow = 0
        table = $(".gridtable>tbody>tr")
        table.each() { row ->
          if(row.text().contains("Gradle In Action")) {
	
	        // verify the td column's data is what we expect
			// The first column is the one we revised above
	        assert row.find("td", 1).text() == "**REVISED** Gradle In Action"
	        assert row.find("td", 2).text() == "eBook"
	        assert row.find("td", 3).text() == "Drive 3"
	        assert row.find("td", 4).text() == "Gradle Folder"
	
	        foundARow = 1
          }
        }
        // use a "flag" to determine we actually found the item we added
        assert foundARow == 1
    }			
	
    @Test	
    void trackMyStuffDelete() {
        to TrackMyStuffHomepage

        // CSS selector for "new" href and click
        $("html>body>p>a").click()
		
        // ------------------------------------------------
        // Create new data, so that we have something to delete
        // ------------------------------------------------

        // Select and set the form inputs
        $("#name").value("Groovy In Action")
        $("#item_type").value("Book")
        $("#item_location_1").value("Bookshelf 3")
        $("#item_location_2").value("Shelf 1")
        $("#description").value("Groovy is cool!")

        // Click the Form's submit button
        def newForm = $("html>body>form")
        $(type: "submit").click()	

        // ------------------------------------------------
        // Verify the new data is there:
        // ------------------------------------------------

        // Traverse the table rows, looking for one with the book title we added
        // And then verify the contents
		// TODO: Is there a better way to do this using selectors?
        def foundARow = 0
        def table = $(".gridtable>tbody>tr")
        table.each() { row ->
          if(row.text().contains("Groovy In Action")) {
	
	        // verify the td column's data is what we expect
	        assert row.find("td", 1).text() == "Groovy In Action"
	        assert row.find("td", 2).text() == "Book"
	        assert row.find("td", 3).text() == "Bookshelf 3"
	        assert row.find("td", 4).text() == "Shelf 1"
	
	        foundARow = 1
          }
        }
        // use a "flag" to determine we actually found the item we added
        assert foundARow == 1

        // ------------------------------------
        // Delete what we just created
        // ------------------------------------
        foundARow = 0

        // Walk the table until we find the row we want
        // and click the delete anchor
        table = $(".gridtable>tbody>tr")
        table.each() { row ->

          // We want the one we created/updated
          if(row.text().contains("Groovy In Action")) {

            // DEBUG: Walk all the columns of the TD row  
            //def tdRow = row.find("td")
            //tdRow.each() { tdItem ->
            //  println "GOT A TD HERE: " + tdItem.text()
       	    //}
	
	        // get thw 7th column's anchor, the delete href
		    foundARow = 1			
	        row.find("td", 6).find("a").click()
          }
        }
	   
        // use a "flag" to determine we actually found the item we added
        assert foundARow == 1	   
		
        // Verify we are on the correct page
        assert $("html>body>p").text().contains(
            "Are you sure you want to delete the following item with ID")

       // Now select the form and click the "Yes. Delete It" button
       def deleteForm = $("html>body>form")
       // The submit is the 2nd input
       deleteForm.find("input", 1).click()
		
    }		
}