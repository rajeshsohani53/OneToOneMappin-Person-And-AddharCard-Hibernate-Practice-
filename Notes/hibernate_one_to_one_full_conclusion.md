# Hibernate One-to-One Relationship — Full Conclusion

मान लो हमारे पास दो classes हैं:

```java
Person
Aadhar
```

और relationship है:

```text
One Person → One Aadhar
One Aadhar → One Person
```

इसलिए यह **One-to-One Bidirectional Relationship** है।

---

## 1. दोनों classes एक-दूसरे का reference रख सकती हैं

Person class के अंदर:

```java
private Aadhar aadhar;
```

इसका मतलब:

> Person के पास Aadhar का object/reference है।

और Aadhar class के अंदर:

```java
private Person person;
```

इसका मतलब:

> Aadhar के पास Person का object/reference है।

इसलिए दोनों classes एक-दूसरे को reference कर रही हैं।

इसीलिए इसे **Bidirectional Relationship** कहते हैं।

---

## 2. लेकिन दोनों Owner नहीं होते

यहाँ सबसे important बात है:

> **जिस class के अंदर दूसरे object का reference है, इसका मतलब यह नहीं है कि वही Owning Side है।**

Owning Side का decision इस बात से होता है कि:

> **Foreign Key किस side की table में है और कौन-सी side उस relationship को database में manage कर रही है।**

हमारे example में हमने Foreign Key Aadhar table में रखी:

```text
Aadhar.person_id → Person.id
```

इसलिए:

```text
Aadhar = Owning Side
Person = Inverse / Non-Owning Side
```

---

## 3. Aadhar Owning Side क्यों है?

Aadhar class:

```java
@Entity
public class Aadhar {

    @Id
    private int id;

    private String aadharNumber;
    private String dob;
    private String address;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
```

यहाँ:

```java
@JoinColumn(name = "person_id")
```

का मतलब है:

> Aadhar table में `person_id` नाम का Foreign Key column रहेगा।

और यह Foreign Key Person table की Primary Key को reference करेगी।

Database में roughly:

```text
PERSON
----------------
id (PK)
name
```

और:

```text
AADHAR
----------------
id (PK)
aadhar_number
dob
address
person_id (FK)
```

Relationship:

```text
Aadhar.person_id
       ↓
Person.id
```

---

## 4. `person_id` नाम कहाँ से आया?

यह नाम Hibernate ने compulsory नहीं दिया।

हमने खुद लिखा:

```java
@JoinColumn(name = "person_id")
```

इसलिए database में column का नाम:

```text
person_id
```

होगा।

हम चाहें तो:

```java
@JoinColumn(name = "my_person_fk")
```

भी लिख सकते हैं।

तब column का नाम:

```text
my_person_fk
```

होगा।

इसलिए:

> `@JoinColumn(name = "...")` के अंदर जो नाम है, वह **database column का नाम** है।

---

## 5. Hibernate को कैसे पता चलता है कि यह Person की Primary Key को reference करेगा?

Aadhar में:

```java
private Person person;
```

यहाँ `Person` एक Entity है।

और Person class में:

```java
@Id
private int id;
```

Hibernate को पता है कि:

```text
Person.id = Primary Key
```

इसलिए Aadhar का:

```text
person_id
```

Person की:

```text
id
```

को reference करेगा।

Conceptually:

```text
Aadhar object
     ↓
person reference
     ↓
Person object
     ↓
Person.id
     ↓
Aadhar.person_id
```

---

## 6. अब Person side समझो

Person class:

```java
@Entity
public class Person {

    @Id
    private int id;

    private String name;

    @OneToOne(mappedBy = "person")
    private Aadhar aadhar;
}
```

यहाँ:

```java
private Aadhar aadhar;
```

का मतलब:

> Person के पास Aadhar का reference है।

लेकिन:

```java
mappedBy = "person"
```

का मतलब यह नहीं है कि `person` Person class का नाम है।

और यह database column का नाम भी नहीं है।

यह **Aadhar class के अंदर मौजूद Java field का नाम है**:

```java
private Person person;
```

इसलिए:

```java
mappedBy = "person"
```

का `person` exactly इस field से match करता है:

```java
private Person person;
```

---

## 7. `mappedBy` को आसान भाषा में समझो

Person कह रहा है:

> "मेरे पास Aadhar का object है, लेकिन इस relationship की database mapping मैं खुद manage नहीं कर रहा हूँ। Aadhar class के अंदर जो `person` field है, वही इस relationship को manage कर रही है।"

इसलिए:

```java
@OneToOne(mappedBy = "person")
private Aadhar aadhar;
```

---

## 8. दोनों references होने के बावजूद Owner एक ही क्यों?

हमारे पास:

```text
Person  ←────────→  Aadhar
   │                  │
   │                  │
aadhar              person
reference           reference
```

दोनों references हैं।

लेकिन database में Foreign Key एक जगह रखी है:

```text
AADHAR
   |
   └── person_id (FK)
```

इसलिए केवल Aadhar side relationship की **owning side** है।

Person side:

```text
mappedBy
```

के कारण **inverse/non-owning side** है।

---

# Final Mental Model

पूरे concept को ऐसे याद रख:

```text
             JAVA OBJECT LEVEL

       Person  ←────────→  Aadhar
          │                  │
          │                  │
       aadhar             person
       reference          reference


          DATABASE LEVEL

       PERSON TABLE
       ------------
       id (PK)
       name
            ↑
            │
            │ referenced by
            │
       AADHAR TABLE
       ------------
       id (PK)
       aadhar_number
       dob
       address
       person_id (FK)
```

इसलिए:

```text
Person
  ↓
has Aadhar
  ↓
private Aadhar aadhar
```

और:

```text
Aadhar
  ↓
belongs to Person
  ↓
private Person person
```

लेकिन:

```text
Aadhar
  ↓
@JoinColumn
  ↓
Foreign Key
  ↓
OWNING SIDE
```

और:

```text
Person
  ↓
mappedBy
  ↓
INVERSE / NON-OWNING SIDE
```

---

# सबसे आखिरी में 5 चीजें याद रखनी हैं

### `@OneToOne`

बोलता है:

> एक object का दूसरे object के साथ one-to-one relationship है।

### `@JoinColumn`

बोलता है:

> Relationship की Foreign Key इस side के table में है।

### `mappedBy`

बोलता है:

> Relationship दूसरी side के field द्वारा manage हो रही है।

### Owning Side

बोलता है:

> जिसके पास Foreign Key की mapping है।

### Inverse / Non-Owning Side

बोलता है:

> जिसके पास relationship का reference तो है, लेकिन Foreign Key की ownership नहीं है।

---

## One-line Conclusion

> **दोनों classes एक-दूसरे का reference रख सकती हैं, इसलिए relationship Bidirectional है; लेकिन जिस side की table में Foreign Key होती है और जिस side पर `@JoinColumn` लगा होता है, वही Owning Side होती है। दूसरी side पर `mappedBy` होता है, इसलिए वह Inverse या Non-Owning Side होती है। `mappedBy` के अंदर हमेशा Owning Side के Java field का exact नाम लिखा जाता है, जबकि `@JoinColumn(name = "...")` में database Foreign Key column का नाम लिखा जाता है।**


## Hibernate One-to-One Relationship — Full Conclusion

मान लो हमारे पास दो classes हैं:

```java
Person
Aadhar
```

और relationship है:

```text
One Person → One Aadhar
One Aadhar → One Person
```

इसलिए यह **One-to-One Bidirectional Relationship** है।

---

### 1. दोनों classes एक-दूसरे का reference रख सकती हैं

Person class के अंदर:

```java
private Aadhar aadhar;
```

इसका मतलब:

> Person के पास Aadhar का object/reference है।

और Aadhar class के अंदर:

```java
private Person person;
```

इसका मतलब:

> Aadhar के पास Person का object/reference है।

इसलिए दोनों classes एक-दूसरे को reference कर रही हैं।

इसीलिए इसे **Bidirectional Relationship** कहते हैं।

---

### 2. लेकिन दोनों Owner नहीं होते

यहाँ सबसे important बात है:

> **जिस class के अंदर दूसरे object का reference है, इसका मतलब यह नहीं है कि वही Owning Side है।**

Owning Side का decision इस बात से होता है कि:

> **Foreign Key किस side की table में है और कौन-सी side उस relationship को database में manage कर रही है।**

हमारे example में हमने Foreign Key Aadhar table में रखी:

```text
Aadhar.person_id → Person.id
```

इसलिए:

```text
Aadhar = Owning Side
Person = Inverse / Non-Owning Side
```

---

### 3. Aadhar Owning Side क्यों है?

Aadhar class:

```java
@Entity
public class Aadhar {

    @Id
    private int id;

    private String aadharNumber;
    private String dob;
    private String address;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
```

यहाँ:

```java
@JoinColumn(name = "person_id")
```

का मतलब है:

> Aadhar table में `person_id` नाम का Foreign Key column रहेगा।

और यह Foreign Key Person table की Primary Key को reference करेगी।

Database में roughly:

```text
PERSON
----------------
id (PK)
name
```

और:

```text
AADHAR
----------------
id (PK)
aadhar_number
dob
address
person_id (FK)
```

Relationship:

```text
Aadhar.person_id
       ↓
Person.id
```

---

### 4. `person_id` नाम कहाँ से आया?

यह नाम Hibernate ने compulsory नहीं दिया।

हमने खुद लिखा:

```java
@JoinColumn(name = "person_id")
```

इसलिए database में column का नाम:

```text
person_id
```

होगा।

हम चाहें तो:

```java
@JoinColumn(name = "my_person_fk")
```

भी लिख सकते हैं।

तब column का नाम:

```text
my_person_fk
```

होगा।

इसलिए:

> `@JoinColumn(name = "...")` के अंदर जो नाम है, वह **database column का नाम** है।

---

### 5. Hibernate को कैसे पता चलता है कि यह Person की Primary Key को reference करेगा?

Aadhar में:

```java
private Person person;
```

यहाँ `Person` एक Entity है।

और Person class में:

```java
@Id
private int id;
```

Hibernate को पता है कि:

```text
Person.id = Primary Key
```

इसलिए Aadhar का:

```text
person_id
```

Person की:

```text
id
```

को reference करेगा।

Conceptually:

```text
Aadhar object
     ↓
person reference
     ↓
Person object
     ↓
Person.id
     ↓
Aadhar.person_id
```

---

### 6. अब Person side समझो

Person class:

```java
@Entity
public class Person {

    @Id
    private int id;

    private String name;

    @OneToOne(mappedBy = "person")
    private Aadhar aadhar;
}
```

यहाँ:

```java
private Aadhar aadhar;
```

का मतलब:

> Person के पास Aadhar का reference है।

लेकिन:

```java
mappedBy = "person"
```

का मतलब यह नहीं है कि `person` Person class का नाम है।

और यह database column का नाम भी नहीं है।

यह **Aadhar class के अंदर मौजूद Java field का नाम है**:

```java
private Person person;
```

इसलिए:

```java
mappedBy = "person"
```

का `person` exactly इस field से match करता है:

```java
private Person person;
```

---

### 7. `mappedBy` को आसान भाषा में समझो

Person कह रहा है:

> "मेरे पास Aadhar का object है, लेकिन इस relationship की database mapping मैं खुद manage नहीं कर रहा हूँ। Aadhar class के अंदर जो `person` field है, वही इस relationship को manage कर रही है।"

इसलिए:

```java
@OneToOne(mappedBy = "person")
private Aadhar aadhar;
```

---

### 8. दोनों references होने के बावजूद Owner एक ही क्यों?

हमारे पास:

```java
Person
    ↓
Aadhar
```

और:

```java
Aadhar
    ↓
Person
```

दोनों references हैं।

लेकिन database में Foreign Key एक जगह रखी है:

```text
AADHAR
   |
   └── person_id (FK)
```

इसलिए केवल Aadhar side relationship की **owning side** है।

Person side:

```text
mappedBy
```

के कारण **inverse/non-owning side** है।

---

## Final Mental Model

पूरे concept को ऐसे याद रख:

```text
             JAVA OBJECT LEVEL

       Person  ←────────→  Aadhar
          │                  │
          │                  │
       aadhar             person
       reference          reference


          DATABASE LEVEL

       PERSON TABLE
       ------------
       id (PK)
       name
            ↑
            │
            │ referenced by
            │
       AADHAR TABLE
       ------------
       id (PK)
       aadhar_number
       dob
       address
       person_id (FK)
```

इसलिए:

```text
Person
  ↓
has Aadhar
  ↓
private Aadhar aadhar
```

और:

```text
Aadhar
  ↓
belongs to Person
  ↓
private Person person
```

लेकिन:

```text
Aadhar
  ↓
@JoinColumn
  ↓
Foreign Key
  ↓
OWNING SIDE
```

और:

```text
Person
  ↓
mappedBy
  ↓
INVERSE / NON-OWNING SIDE
```

---

## सबसे आखिरी में 5 चीजें याद रखनी हैं

### `@OneToOne`

बोलता है:

> एक object का दूसरे object के साथ one-to-one relationship है।

### `@JoinColumn`

बोलता है:

> Relationship की Foreign Key इस side के table में है।

### `mappedBy`

बोलता है:

> Relationship दूसरी side के field द्वारा manage हो रही है।

### Owning Side

बोलता है:

> जिसके पास Foreign Key की mapping है।

### Inverse / Non-Owning Side

बोलता है:

> जिसके पास relationship का reference तो है, लेकिन Foreign Key की ownership नहीं है।

---

### One-line conclusion:

> **दोनों classes एक-दूसरे का reference रख सकती हैं, इसलिए relationship Bidirectional है; लेकिन जिस side की table में Foreign Key होती है और जिस side पर `@JoinColumn` लगा होता है, वही Owning Side होती है। दूसरी side पर `mappedBy` होता है, इसलिए वह Inverse या Non-Owning Side होती है। `mappedBy` के अंदर हमेशा Owning Side के Java field का exact नाम लिखा जाता है, जबकि `@JoinColumn(name = "...")` में database Foreign Key column का नाम लिखा जाता है।**