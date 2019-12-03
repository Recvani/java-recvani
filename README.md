# java-recvani
This contains the client side api for use with recvani server. Recvani is collabrative learning platform for any bussiness  where a user and item interact. So client can send score for user interactions and ask for recommendation for user. And all these things in real time.

Check us out  at https://www.recvani.com
## Features
* **Ease to integration:** 

 &nbsp;&nbsp;&nbsp;&nbsp;Integrating is as simple as integrating a database.
* **Real time system:** 
    
 &nbsp;&nbsp;&nbsp;&nbsp;User interaction are taken care within milliseconds for recommendation
* **Can Scale very easily:** 

 &nbsp;&nbsp;&nbsp;&nbsp;It can easily support upto millions of users. 
* **Low on cost:** 

 &nbsp;&nbsp;&nbsp;&nbsp;Cost for deploying will be much less than your inhouse machine learning cost.
* **Support categorization:** 

 &nbsp;&nbsp;&nbsp;&nbsp;Client can easily ask for specific category of item for a user.
    

## Installation
We can directly use it directly from maven repo

    <dependency>
        <groupId>com.recvani</groupId>
        <artifactId>java-recvani</artifactId>
        <version>0.0.2</version>
    </dependency>

For more details check https://mvnrepository.com/artifact/com.recvani/java-recvani

## Examples
Client need model name, client key and model key for connecting to recvani server. You can get these by contacting us.

### Creating connection 

Setting up connection is quite easy. 

    import com.recvani.manager.RecVaniManager;
    //Don't forget to replace these by the keys you will get.
    recVaniManager = new RecVaniManager(CLIENT_KEY, MODEL_NAME, MODEL_KEY);

### Sending Interacations

#### Send Single Interaction
    
    import com.recvani.requests.SimpleInteraction;
    
    String USER = "USER1";           // unique id for user
    String ITEM = "ITEM1";           // item id (item can be news id, video id, product id etc.)
    double SCORE = 1.0;              // score client want to give for interaction. Should be devised intelligently
    long ITIME = System.currentTimeMillis()/1000; # time for the interaction in seconds
    
    SimpleInteraction si = new SimpleInteraction(USER, ITEM, SCORE, ITIME);
    
    boolean result = recVaniManager.sendRequest(si); //  
        
   
#### Send Batch Interactions
    
    import com.recvani.requests.SimpleInteraction;
    import com.recvani.requests.BatchInteraction;
   
    List<SimpleInteraction> interactions = new ArrayList<>();
    long time = System.currentTimeMillis()/1000;
    interactions.add(new SimpleInteraction("USER1", "ITEM1", 0.0, time));
    interactions.add(new SimpleInteraction("USER2", "ITEM1", 1.0, time));
    interactions.add(new SimpleInteraction("USER1", "ITEM2", 0.0, time));
    
    BatchInteraction batchInteraction = new BatchInteraction();
    boolean result = recVaniManager.sendRequest(batchInteraction);


### Sending Parameters


We can attach tags and expiry time for every item
    
#### Send expiry time 
    
    import com.recvani.requests.ExpRequest;
    
    long time = System.currentTimeMillis()/1000;
    ExpRequest expRequest  = new ExpRequest("ITEM1", (time + 365*24*3600));
    boolean result = recVaniManager.sendRequest(expRequest);
         

#### Send tags 
    
    import com.recvani.requests.ExpRequest;
    import java.util.List;
    import java.util.ArrayList;

    List<String> tags = new ArrayList<>();
    tags.add("ST1");
    
    TagRequest tagRequest = new TagRequest("ITEM1", tags);
    boolean result = recVaniManager.sendRequest(tagRequest);

    
#### Send in bulk
    
    import com.recvani.requests.ItemParam;
    import com.recvani.requests.BatchParam;
    import java.util.List;
    import java.util.ArrayList;

    long time = System.currentTimeMillis()/1000;
    List<String> tags1 = new ArrayList<>();
    tags1.add("ST1");
    List<String> tags2 = new ArrayList<>();
    tags2.add("ST2");
    List<ItemParam>  itemParams = new ArrayList<>();
    itemParams.add(new ItemParam("ITEM1", tags1, time + 365*24*3600 ));
    itemParams.add(new ItemParam("ITEM2", tags2));
    itemParams.add(new ItemParam("ITEM3", time + 365*24*3600));
    BatchParam bparam = new BatchParam(itemParams);
    boolean result = recVaniManager.sendRequest(bparam);
    

### Getting Result

We can get the final recommendation for the user. We can filter history and send you items for particular tag.

#### Overall Recommendation

    import java.util.List;
    import java.util.ArrayList;

   
    String USER = "USER1";      // USER ID
    int COUNT = 10;             // Count of Recommended item to fetch
    List<List<String>> TAGS = new ArrayList<>();           // Tags of item, Empty for overall 
    bool HISTORY = false;       // Will not serve already serverd item.
    
    List<String>  result = recVaniManager.getStories(USER, COUNT, TAGS, HISTORY); // Result will be list of items
  
#### Tagged Recommedation

    List<String> tags2 = new ArrayList<>();
    tags2.add("ST2");
    List<List<String>> tags = new ArrayList<>();
    tags.add(tags2);
    List<String>  result = recVaniManager.getStories("USER1", 10, tags, false); // Result will be list of items with tag ST2

#### Complex Queries 

Tags can be used to make complex queries. For Example 

    List<String> tags1 = new ArrayList<>();
    tags1.add("TAG1");
    tags1.add("TAG2");
    
    List<String> tags2 = new ArrayList<>();
    tags2.add("TAG4");

    List<List<String>> TAGS = new ArrayList<>();
    tags.add(tags1);
    tags.add(tags2);
    
The inner lists provide intersection and outer lists provide union. The tags above will return  all time which are either marked "TAG4" or have both "TAG1" and "TAG2"  attached to it.

For any help feel free to contact anshuman@recvani.com
