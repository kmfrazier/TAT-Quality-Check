FULL SERVICE ACCOUNT USE CASE DESCRIBED HERE:
https://docs.google.com/document/d/1wJ1UlpC1uPXUoOdj2uc2y1ZCFH9HwBU3m7QLGN2Vu94

This program evaluates all the headers of the language spreadsheets on the TAT TALL Google Drive folder here: https://drive.google.com/drive/u/2/folders/0AHJa0Pgl9dbFUk9PVA

As of 4/22/2022 it checks each sheet's tabs containing titles "Master List" or "Alphabet Embark" for duplicate headers. More tab names can be added in the run() function of DriveUtils.

To see output, run the program and open the following url (takes about 5 minutes to run):
http://localhost:7010/api/v2/language

File flow is as follows:

* Process
* LanguageApiConfig
* LanguageController
* LanguageService
* LanguageDao
* DriveUtils
* SaService
* DriveService

Once the above url is opened, the program will retrieve a list of languages and spreadsheet urls from a database hosted on AWS.

Then, the DriveUtils file will evaluate each header found at each doc_url contained in the languages response. It will pass the private ServiceAccountKey as a credential in order to authenticate into the organization Drive folder.