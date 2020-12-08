# event_stream
Features : 
  1. Notification (dummy API) will be pushed when the user pays his first bill
  2. If a user pays 5 or more bills with value >= 20000 within 5 minutes , a notification is triggered
  3. If a user doesn't gives feedback within fifteen minutes of his bill pay, a notification is triggered.
  
What Happens as you save your bill ? - [localhost:8080/cube/scm/events/save]
1. Event is posted - Event json is sent as request body
2. Check if the noun is 'bill'
3. Check if it's the first time for the user to bill
4. Once saved, if its user's first billing then, a notification is triggered. (Here, a api call to localhost:8080/cube/scm/pushnotifications happen with request body containing notification entity with necessary details to be persisted into the database under notification relation.
5. In job_check, its is tagged under the name "FDBK_CHK"

For features 2 & 3, Scheduled tasks will be run. 
for feature 3, the Scheduled task will check a relation called "job_check" where a insert is made when bill is saved. 
If the bill stays in job_check for more than 15 mins , then it will be notified. An entry in notification realation will be made.
If feedback is given, during the event save, a delete of bill by the user in job_check relation is done.

For feature 2, Scheduled task will check the event relation for once every minute.
1. for this feature , we have a tag in job_check called "GRTVALUE"
2.Check for the last bill that was considered for this feature.
3. List of event Ids will be taken from the event relation to know if a user has paid more than 5 bils within 5 mins with value >=20000.
4. A call to notification will be done.
5.A post to job check is done to mark the last eventID to be verified with tag "GRTVALUE".


