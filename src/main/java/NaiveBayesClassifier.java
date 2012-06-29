
/**
 * Created with IntelliJ IDEA.
 * User: snark
 * Created Date: 6/28/12
 * Created Time: 8:46 AM
 * @author Michael
 */
import quickdt.Attributes;
import quickdt.Instance;

import java.io.Serializable;
import java.util.*;

/**
 * This Classifier uses Maximum Likelihood to estimate from the training set.
 */
public class NaiveBayesClassifier {

    public HashMap<Serializable, ArrayList<Attributes>> trainingset;

    public NaiveBayesClassifier(){
        trainingset = new HashMap<Serializable, ArrayList<Attributes>>();
    }

    public void addInstances(List<Instance> instances){
        for(Instance i : instances){
            train(i);
        }
    }

    /**
     * Add an instance to our training set.
     * @param i An instance.
     */
    private void train(Instance i){
        if(!trainingset.keySet().contains(i.classification)){
            trainingset.put(i.classification, new ArrayList<Attributes>());
        }
        trainingset.get(i.classification).add(i.attributes);
    }

    /**
     * Compute P(F|C).
     * The attribute count is 1 by default to avoid making the probability 0.
     * @param s The string associated with the attribute
     * @param o The object associated with the attribute
     * @param c An object representing a classification.
     * @return The conditional probability P(Fi|C).
     */
    private double conditionalProbability(String s, Serializable o, Serializable c){
        int totalcount = trainingset.get(c).size();
        System.out.println("Number of attributes in " + c + " classifcation: " + trainingset.get(c).size());
        int attributecount = 0;
        double conditionalprob = 0.0;

        for(Attributes a : trainingset.get(c)){
            if(a.get(s) != null && a.get(s).equals(o)){
                attributecount++;
            }
        }

        attributecount = (attributecount == 0) ? 1 : attributecount; //If there are no attributes, add 1 for smoothing.

        conditionalprob = (double) attributecount / (double) totalcount;
        return conditionalprob;
    }

    /**
     * Find class corresponding to the max P(C)*P(Fi|C)
     * @param a A set of attributes.
     * @return The most likely
     */
    public Serializable predictClass(Attributes a){
        double classprob = 1/ (double) trainingset.size();
        double finalprob = 0;
        Serializable classification = null;
        for(Serializable c : trainingset.keySet()){
            double conditionalprob = 1.0;
            for(String s : a.keySet()){
                conditionalprob *= conditionalProbability(s, a.get(s), c);
            }
            if(classprob*conditionalprob > finalprob){
                finalprob = classprob*conditionalprob;
                classification = c;
            }
        }
        return classification;

    }

    public static void main(String args[]){
        ArrayList<Instance> instances = new ArrayList<Instance>();
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("republican"));
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("democrat"));
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("republican"));
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("republican"));
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("libertarian"));
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "white").classification("republican"));
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "black").classification("democrat"));
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "black").classification("democrat"));
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "black").classification("democrat"));
        instances.add(Attributes.create("state", "ohio", "gender", "male", "race", "black").classification("republican"));
        instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "white").classification("democrat"));
        instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "white").classification("democrat"));
        instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "white").classification("republican"));
        instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "white").classification("republican"));
        instances.add(Attributes.create("state", "ohio", "gender", "female", "race", "white").classification("democrat"));
        instances.add(Attributes.create("state", "texas", "gender", "male", "race", "white").classification("libertarian"));
        instances.add(Attributes.create("state", "kansas", "gender", "male", "race", "white").classification("libertarian"));
        instances.add(Attributes.create("state", "texas", "gender", "male", "race", "white").classification("libertarian"));
        instances.add(Attributes.create("state", "texas", "gender", "male", "race", "white").classification("libertarian"));
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
        instances.add(Attributes.create("state", "new york", "gender", "male", "race", "white").classification("republican"));


        NaiveBayesClassifier NBC = new NaiveBayesClassifier();
        NBC.addInstances(instances);
        System.out.println(NBC.conditionalProbability("state", "texas", "libertarian"));
        System.out.println(NBC.predictClass(Attributes.create("state", "new york", "gender", "female")));
    }

}