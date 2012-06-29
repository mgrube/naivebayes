
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

    private HashMap<Serializable, ArrayList<Attributes>> trainingset;

    public HashMap<Serializable, ArrayList<Attributes>> getTrainingset(){
        return trainingset;
    }

    public NaiveBayesClassifier(){
        trainingset = new HashMap<Serializable, ArrayList<Attributes>>();
    }

    /**
     * Convenience function for adding a list of instances.
     * @param instances
     */
    public void addInstances(List<Instance> instances){
        for(Instance i : instances){
            train(i);
        }
    }

    /**
     * Add an instance to our training set.
     * @param i An instance.
     */
    public void train(Instance i){
        if(!trainingset.containsKey(i.classification)){
            trainingset.put(i.classification, new ArrayList<Attributes>());
        }
        trainingset.get(i.classification).add(i.attributes);
    }

    /**
     * Compute P(Fi|C).
     * The attribute count is 1 by default to avoid making the probability 0.
     * @param s The string associated with the attribute
     * @param o The object associated with the attribute
     * @param c An object representing a classification.
     * @return The conditional probability P(Fi|C).
     */
    public double conditionalProbability(String s, Serializable o, Serializable c){
        int totalcount = trainingset.get(c).size();
        int attributecount = 0;
        double conditionalprob = 0.0;

        for(Attributes a : trainingset.get(c)){
            if(a.get(s) != null && a.get(s).equals(o)){
                attributecount++;
            }
        }

        attributecount = (attributecount == 0) ? 1 : attributecount; //If there are no attributes, add 1 to prevent 0 probability.

        conditionalprob = (double) attributecount / (double) totalcount;
        return conditionalprob;
    }

    /**
     * Find class corresponding to the max P(C)*P(Fi|C)
     * @param a A set of attributes.
     * @return The maximum value of P(C)*P(Fi|C) over all classifications.
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

}