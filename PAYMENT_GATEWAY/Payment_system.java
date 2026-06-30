import java.util.*;

class PaymentRequest{
    public String sender;
    public String receiver;
    public double amount;
    public String currency;

    public PaymentRequest(String s,String r,double a,String c){
        this.sender=s;
        this.receiver=r;
        this.amount=a;
        this.currency=c;
    }
}
interface BankingSystem{
    boolean processPayment(double amount);
}
class PaytmBankingSystem implements BankingSystem{
    private Random rand= new Random();
    public PaytmBankingSystem(){}
    @Override
    public boolean processPayment(double amount){
        System.out.println("[BankingSystem-Paytm] Processing payment of " + amount + "...");
        int r=rand.nextInt(100);
        return r<80;
    }

}
class RazorpayBankingSystem implements BankingSystem {
    private Random rand = new Random();

    public RazorpayBankingSystem() {}

    @Override
    public boolean processPayment(double amount) {
        System.out.println("[BankingSystem-Razorpay] Processing payment of " + amount + "...");
        int r = rand.nextInt(100);
        return r < 90;
    }
}
abstract class PaymentGateway{
    protected BankingSystem bs;
    public PaymentGateway(){
        this.bs=null;
    }
    protected abstract boolean validatePayment(PaymentRequest req);
    protected abstract boolean initiatePayment(PaymentRequest request);
    protected abstract boolean confirmPayment(PaymentRequest request);

    public boolean processPayment(PaymentRequest request){
        if(!validatePayment(request)){
            System.out.println("[PaymentGateway] Validation failed for " + request.sender + ".");
            return false;
        }
        if (!initiatePayment(request)) {
            System.out.println("[PaymentGateway] Initiation failed for " + request.sender + ".");
            return false;
        }
        if (!confirmPayment(request)) {
            System.out.println("[PaymentGateway] Confirmation failed for " + request.sender + ".");
            return false;
        }
        return true;
    }
}
class PaytmGateway extends PaymentGateway{
    public PaytmGateway(){
        this.bs=new PaytmBankingSystem();
    }
    @Override
    protected boolean validatePayment(PaymentRequest r){
        System.out.println("[Paytm] Validating payment for "+r.sender+".");
        if(r.amount<=0 || !"INR".equals(r.currency)){
            return false;
        }
        return true;
    }
    @Override
    protected boolean initiatePayment (PaymentRequest r){
        System.out.println("[Paytm] Initiating payment of "+r.amount+" "+r.currency+" for "+r.sender+".");
        return bs.processPayment(r.amount);
    }
    @Override
    protected boolean confirmPayment(PaymentRequest r){
        System.out.println("[Paytm] Confirming payment for "+r.sender+".");
        return true;
    }
}
class RazorpayGateway extends PaymentGateway {
    public RazorpayGateway() {
        this.bs = new RazorpayBankingSystem();
    }
    @Override
    protected boolean validatePayment(PaymentRequest request) {
        System.out.println("[Razorpay] Validating payment for " + request.sender + ".");
        if (request.amount <= 0) {
            return false;
        }
        return true;
    }
    @Override
    protected boolean initiatePayment(PaymentRequest request) {
        System.out.println("[Razorpay] Initiating payment of " + request.amount
                + " " + request.currency + " for " + request.sender + ".");
        return bs.processPayment(request.amount);
    }
    @Override
    protected boolean confirmPayment(PaymentRequest request) {
        System.out.println("[Razorpay] Confirming payment for " + request.sender + ".");
        return true;
    }
}
class PaymentGatewayProxy extends PaymentGateway{
    private PaymentGateway realPg;
    private int retries;
    public PaymentGatewayProxy(PaymentGateway pg, int maxRetries){
        this.realPg=pg;
        this.retries=maxRetries;
    }
    @Override
    public boolean processPayment(PaymentRequest r){
        boolean result=false;
        for(int attempt=0;attempt<retries;++attempt){
            if(attempt>0){
                System.out.println("[Proxy] Retrying payment (attempt "+attempt+1+" ) for "+r.sender+ ".");            
            }
            result=realPg.processPayment(r);
            if(result)break;
        }
        if(!result){
            System.out.println("[Proxy] Payment failed after " + retries
                    + " attempts for " + r.sender + ".");
        }
        return result;
    }
    @Override
    protected boolean validatePayment(PaymentRequest request) {
        return realPg.validatePayment(request);
    }
    @Override
    protected boolean initiatePayment(PaymentRequest request) {
        return realPg.initiatePayment(request);
    }
    @Override
    protected boolean confirmPayment(PaymentRequest request) {
        return realPg.confirmPayment(request);
    }
}
enum GatewayType{
    PAYTM,
    RAZORPAY
}
class GatewayFactory{
    private static final GatewayFactory instance=new GatewayFactory();
    private GatewayFactory(){}
    public static GatewayFactory getInstance(){
        return instance;
    }
    public PaymentGateway getGateway(GatewayType type){
        if(type==GatewayType.PAYTM){
            PaymentGateway paymentGateway=new PaytmGateway();
            return new PaymentGatewayProxy(paymentGateway,3);
        }else {
            PaymentGateway paymentGateway = new RazorpayGateway();
            return new PaymentGatewayProxy(paymentGateway, 1);
        }
    }
}
class PaymentService{
    private static final PaymentService instance= new PaymentService();
    private PaymentGateway pg;
    private PaymentService(){}
    public static PaymentService getInstance(){
        return instance;
    }
    public void setGateway(PaymentGateway pg){
        this.pg=pg;
    }
    public boolean processPayment(PaymentRequest r){
        if (pg == null) {
            System.out.println("[PaymentService] No payment pg selected.");
            return false;
        }
        return pg.processPayment(r);
    }
}
class PaymentController {
    private static final PaymentController instance = new PaymentController();
    private PaymentController() {}
    public static PaymentController getInstance() {
        return instance;
    }
    public boolean handlePayment(GatewayType type, PaymentRequest req) {
        PaymentGateway paymentGateway = GatewayFactory.getInstance().getGateway(type);
        PaymentService.getInstance().setGateway(paymentGateway);
        return PaymentService.getInstance().processPayment(req);
    }
}
public class Payment_system{
    public static void main(String[] args) {
        PaymentRequest req1 = new PaymentRequest("Aditya", "Shubham", 1000.0, "INR");
        System.out.println("Processing via Paytm");
        System.out.println("------------------------------");
        boolean res1 = PaymentController.getInstance().handlePayment(GatewayType.PAYTM, req1);
        System.out.println("Result: " + (res1 ? "SUCCESS" : "FAIL"));
        System.out.println("------------------------------\n");
        PaymentRequest req2 = new PaymentRequest("Shubham", "Aditya", 500.0, "USD");
        System.out.println("Processing via Razorpay");
        System.out.println("------------------------------");
        boolean res2 = PaymentController.getInstance().handlePayment(GatewayType.RAZORPAY, req2);
        System.out.println("Result: " + (res2 ? "SUCCESS" : "FAIL"));
        System.out.println("------------------------------");
    }
}