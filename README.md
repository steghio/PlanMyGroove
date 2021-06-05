# Plan my Groove
Plan my Groove is a simple Java7+ application to expose a REST API to let users remotely execute Groovy scripts

It was tested on both JDK7 and JDK8 and as of now it exposes functionality to:

- submit a job
- list and search for jobs
- retrieve the result of a job
- delete a job

Where a job is a simple Groovy script. It's possible to submit multiple jobs simultaneously and also run them at the same time.

A simple Java test project is attached as well

JUnit 4 tests are included

For more information, read the documentation at https://github.com/steghio/PlanMyGroove/blob/master/plan_my_groove_manual.pdf

Project is licensed under a [CreativeCommons Attribution-ShareAlike 4.0 International license](https://creativecommons.org/licenses/by-sa/4.0/legalcode)
