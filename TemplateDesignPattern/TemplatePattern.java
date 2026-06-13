package TemplateDesignPattern;

abstract class ModelTrainer{
    protected void loadData(String dataPath){
       System.out.println("Loading Data from "+dataPath);
    }
    protected void preprocess(){
        System.out.println("Splitting into train/test and normalizing");
    }
    protected abstract void trainModel();
    protected abstract void evaluateModel();
    protected void saveModel(){
        System.out.println("Saving model to disk ");
    }
    public final void trainPipeline(String dataPath){
        loadData(dataPath);
        preprocess();
        trainModel();
        evaluateModel();
        saveModel();
    }
}

class NeuralNetwork extends ModelTrainer{
    @Override
    protected void trainModel(){
        System.out.println("Training the Neural Network for 100 epochs");
    }
    @Override
    protected void evaluateModel(){
        System.out.println("Evaluating the accuracy and loss on validation set");
    }
    @Override
    protected void saveModel(){
        System.out.println("Serializing the network weights to .h5 file");
    }
}
class DecisionTree extends ModelTrainer {
    @Override
    protected void trainModel() {
        System.out.println("[DecisionTree] Building decision tree with max_depth=5");
    }

    @Override
    protected void evaluateModel() {
        System.out.println("[DecisionTree] Computing classification report (precision/recall)");
    }
}
public class TemplatePattern {
    public static void main(String[] args) {
        System.out.println("=== Neural Network Training ===");
        ModelTrainer nnTrainer = new NeuralNetwork();
        nnTrainer.trainPipeline("data/images/");

        System.out.println("\n=== Decision Tree Training ===");
        ModelTrainer dtTrainer = new DecisionTree();
        dtTrainer.trainPipeline("data/iris.csv");
    }
}