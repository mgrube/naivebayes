/**
 * Created with IntelliJ IDEA.
 * User: snark
 * Date: 6/27/12
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */

import org.testng.*;
import quickdt.Attributes;
import quickdt.Instance;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class NaiveBayesClassifierTest {


@Test
public void conditionalProbabilityTest(){
    ArrayList<Instance> instances = new ArrayList<Instance>();

    /**
     *
     * This set of instances is small enough that we can easily calculate conditional probability of
     * a feature by hand. We'll use the feature of living in Texas to predict a classification corresponding to
     * a political affiliation.
     *
     * I completely made this data up.
     *
     * By hand we can see the conditional probability of being a texas resident being a libertarian is 3/5 or .6
     *
     */

    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("libertarian"));
    instances.add(Attributes.create("state", "texas", "gender", "male", "race", "white").classification("libertarian"));
    instances.add(Attributes.create("state", "kansas", "gender", "male", "race", "white").classification("libertarian"));
    instances.add(Attributes.create("state", "texas", "gender", "male", "race", "white").classification("libertarian"));
    instances.add(Attributes.create("state", "texas", "gender", "male", "race", "white").classification("libertarian"));
    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("republican"));
    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("republican"));
    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "asian").classification("republican"));
    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("republican"));
    instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "hispanic").classification("republican"));
    instances.add(Attributes.create("state", "new york", "gender", "male", "race", "white").classification("republican"));
    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("democrat"));
    instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "white").classification("democrat"));
    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "black").classification("democrat"));
    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "black").classification("democrat"));
    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "black").classification("democrat"));
    instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "black").classification("republican"));
    instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "white").classification("democrat"));
    instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "white").classification("democrat"));
    instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "white").classification("republican"));
    instances.add(Attributes.create("state", "texas", "gender", "female", "race", "black").classification("democrat"));
    instances.add(Attributes.create("state", "new york", "gender", "female", "race", "black").classification("democrat"));
    instances.add(Attributes.create("state", "new york", "gender", "male", "race", "asian").classification("democrat"));
    instances.add(Attributes.create("state", "new york", "gender", "female", "race", "indian").classification("democrat"));
    instances.add(Attributes.create("state", "new york", "gender", "male", "race", "black").classification("democrat"));
    instances.add(Attributes.create("state", "new york", "gender", "male", "race", "white").classification("democrat"));
    instances.add(Attributes.create("state", "new york", "gender", "female", "race", "white").classification("democrat"));
    instances.add(Attributes.create("state", "new york", "gender", "female", "race", "white").classification("democrat"));
    instances.add(Attributes.create("state", "new york", "gender", "male", "race", "white").classification("democrat"));
    instances.add(Attributes.create("state", "new york", "gender", "male", "race", "white").classification("democrat"));


    NaiveBayesClassifier NBC = new NaiveBayesClassifier();
    NBC.addInstances(instances);
    Assert.assertEquals(NBC.conditionalProbability("state", "texas", "libertarian"), 0.6);

    Assert.assertEquals(2,2);
}

    /**
     * Check to make sure our classifications were added properly. Classes should be Hash keys,
     * Attributes should be stored in a key's respective ArrayList.
     */
    @Test
    public void dataAddTest(){
        ArrayList<Instance> instances = new ArrayList<Instance>();
        instances.add(Attributes.create("state", "ohio", "profession", "teacher").classification("female"));
        instances.add(Attributes.create("state", "ohio", "profession", "accountant").classification("female"));
        instances.add(Attributes.create("state", "ohio", "profession", "electrician").classification("male"));
        NaiveBayesClassifier NBC = new NaiveBayesClassifier();
        NBC.addInstances(instances);


        Assert.assertTrue(NBC.getTrainingset().containsKey("female") && NBC.getTrainingset().containsKey("male"));
        Assert.assertTrue(NBC.getTrainingset().get("female").contains(Attributes.create("state", "ohio", "profession", "teacher").classification("female")));
        Assert.assertTrue(NBC.getTrainingset().get("female").contains(Attributes.create("state", "ohio", "profession", "accountant").classification("female")));
        Assert.assertTrue(NBC.getTrainingset().get("male").contains(Attributes.create("state", "ohio", "profession", "electrician").classification("male")));

    }

    /*
    * Are we really getting argmaxP(C=c)*Pi(P(Fi|C))?
     */
    @Test
    public void classificationTest(){

    }


}
