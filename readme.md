[![Maven](https://img.shields.io/maven-central/v/io.github.dgroup/mbox4j.svg)](https://mvnrepository.com/artifact/io.github.dgroup/mbox4j)
[![Javadocs](http://www.javadoc.io/badge/io.github.dgroup/mbox4j.svg)](http://www.javadoc.io/doc/io.github.dgroup/mbox4j)
[![License: MIT](https://img.shields.io/github/license/mashape/apistatus.svg)](./license.txt)
[![Commit activity](https://img.shields.io/github/commit-activity/y/dgroup/mbox4j.svg?style=flat-square)](https://github.com/dgroup/mbox4j/graphs/commit-activity)

[![Build Status](https://circleci.com/gh/dgroup/mbox4j.svg?style=svg)](https://circleci.com/gh/dgroup/mbox4j)
[![0pdd](http://www.0pdd.com/svg?name=dgroup/mbox4j)](http://www.0pdd.com/p?name=dgroup/mbox4j)
[![Dependency Status](https://requires.io/github/dgroup/mbox4j/requirements.svg?branch=master)](https://requires.io/github/dgroup/mbox4j/requirements/?branch=master)
[![Known Vulnerabilities](https://snyk.io/test/github/dgroup/mbox4j/badge.svg)](https://snyk.io/org/dgroup/project/3bd606b4-323a-4a5e-a9cf-8fe847b7f94d/?tab=dependencies&vulns=vulnerable)

[![DevOps By Rultor.com](http://www.rultor.com/b/dgroup/mbox4j)](http://www.rultor.com/p/dgroup/mbox4j)
[![EO badge](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org/#principles)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Qulice](https://img.shields.io/badge/qulice-passed-blue.svg)](http://www.qulice.com/)
[![SQ maintainability](https://sonarcloud.io/api/project_badges/measure?project=io.github.dgroup%3Ambox4j&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=io.github.dgroup%3Ambox4j)
[![Codebeat](https://codebeat.co/badges/03a70479-61fe-4167-bf43-84dfd78d4cc0)](https://codebeat.co/projects/github-com-dgroup-mbox4j-master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e72eb423424b4b6db9ba64aa97463206)](https://www.codacy.com/app/dgroup/mbox4j?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dgroup/mbox4j&amp;utm_campaign=Badge_Grade)
[![Codecov](https://codecov.io/gh/dgroup/mbox4j/branch/master/graph/badge.svg)](https://codecov.io/gh/dgroup/mbox4j)

## What it is
**mbox4j** is an object-oriented primitives to simplify the manipulations with emails for Java-based applications.

## Principles
[Design principles](http://www.elegantobjects.org#principles) behind mbox4j.

## How to use
Get the latest version [here](https://github.com/dgroup/mbox4j/releases):

```xml
<dependency>
    <groupId>io.github.dgroup</groupId>
    <artifactId>mbox4j</artifactId>
    <version>${version}</version>
</dependency>
```

Java version required: 1.8+.

Interface           | Purpose                                               | Implementations / Related                    
--------------------|-------------------------------------------------------|---------------------------
[Inbox](#inbox)     | Allows to read the emails from the server             | [JavaxMailInbox](src/main/java/io/github/dgroup/poi/inbox/javax/JavaxMailInbox.java), [InboxEnvelope](src/main/java/io/github/dgroup/poi/inbox/InboxEnvelope.java), [UncheckedInbox](src/main/java/io/github/dgroup/poi/inbox/UncheckedInbox.java), [FakeInbox](src/main/java/io/github/dgroup/poi/inbox/FakeInbox.java), [etc](src/main/java/io/github/dgroup/poi/inbox)                            
[Outbox](#outbox)   | Allows to send the emails to the target recipients    | [JavaxMailOutbox](src/main/java/io/github/dgroup/poi/outbox/javax/JavaxMailInboxTest.java), [OutboxEnvelope](src/main/java/io/github/dgroup/poi/outbox/OutboxEnvelope.java), [UncheckedOutbox](src/main/java/io/github/dgroup/poi/outbox/UncheckedOutbox.java), [FakeOutbox](src/main/java/io/github/dgroup/poi/outbox/FakeOutbox.java), [etc](src/main/java/io/github/dgroup/poi/outbox)                    
[Msg](#msg)         | The email message to be read/sent                     | [MsgOf](src/main/java/io/github/dgroup/poi/msg/MsgOf.java), [MsgEnvelope](src/main/java/io/github/dgroup/poi/msg/MsgEnvelope.java), [FakeMsg](src/main/java/io/github/dgroup/poi/msg/FakeMsg.java), [etc](src/main/java/io/github/dgroup/poi/msg)
[Query](#query)     | Email search query to the server                      | [QueryOf](src/main/java/io/github/dgroup/poi/query/QueryOf.java), [Mode](src/main/java/io/github/dgroup/poi/query/mode/Mode.java), [ModeOf](src/main/java/io/github/dgroup/poi/query/mode/ModeOf.java), [All](src/main/java/io/github/dgroup/poi/query/mode/All.java), [etc](src/main/java/io/github/dgroup/poi/query)            

All examples below are using the following frameworks/libs:
 - [Hamcrest](https://github.com/hamcrest/JavaHamcrest) - Library of matchers, which can be combined in to create flexible expressions of intent in tests.
 - [cactoos](https://github.com/yegor256/cactoos) - Object-Oriented Java primitives, as an alternative to Google Guava and Apache Commons.
 - [cactoos-matchers](https://github.com/yegor256/cactoos) - Object-Oriented Hamcrest matchers

### [Inbox](src/main/java/io/github/dgroup/poi/Inbox.java)
#### [JavaxMailInbox](src/main/java/io/github/dgroup/poi/inbox/javax/JavaxMailInbox.java)
Fetch the emails from the server using `javax.mail` framework:
```java
public static void main(final String[] args) {
    final Properties smtp = new Properties();
    smtp.setProperty("mail.smtp.host", host);
    smtp.setProperty("mail.smtp.port", port);
    smtp.setProperty("username", user);
    smtp.setProperty("password", password);
    ...
    final Inbox inbox = new JavaxMailInbox(smtp);
    final Iterable<Msg> msgs = inbox.read(
        new Query("pop3s", "INBOX", new All())
    );
    for(final Msg msg : msgs) {
        System.out.println(msg);
    }
}
```
See [Gmail](src/test/java/io/github/dgroup/mbox4j/GmailSmtpProperties.java) SMTP connection properties as an example of a configuration for reading procedure.
### [Outbox](src/main/java/io/github/dgroup/poi/Outbox.java)
#### [JavaxMailOutbox](src/main/java/io/github/dgroup/poi/outbox/javax/JavaxMailInboxTest.java)
Send an email to the target recipients using `javax.mail` framework:
```java
public static void main(final String[] args) {
    final Properties smtp = new Properties();
    smtp.setProperty("mail.smtp.host", host);
    smtp.setProperty("mail.smtp.port", port);
    smtp.setProperty("username", user);
    smtp.setProperty("password", password);
    ...
    final Outbox outbox = new JavaxMailOutbox(smtp);
    outbox.send(
        new MsgOf(
            "from@server.com", 
            "to@server.com", 
            "Testing subj", 
            "I'm simple and i know it."
        )
    );
}
```
See [Gmail](src/test/java/io/github/dgroup/mbox4j/GmailSmtpProperties.java) SMTP connection properties as an example of a configuration for sending procedure. 