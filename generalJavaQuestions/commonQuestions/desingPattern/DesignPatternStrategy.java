package generalJavaQuestions.commonQuestions.desingPattern;

import lombok.extern.slf4j.Slf4j;

public class DesignPatternStrategy {
}


/*without strategy*/
@Slf4j
class PaymentWithoutStrategy{
    /*This is violating the single responsibility and to add any new mode u need to violate the open-close*/
    void pay(int amt, String mode){
        if(mode.equals("CARD")){
            log.info("paid {} via card", amt);
        } else if(mode.equals("CASH")){
            log.info("paid {} via cash", amt);
        } else {
            log.error("{} mode is not accepted", mode);
        }
    }
}

class TestWithoutStrategy{
    public static void main(String[] args) {
        PaymentWithoutStrategy paymentWithoutStrategy = new PaymentWithoutStrategy();
        paymentWithoutStrategy.pay(100, "CASH");
        paymentWithoutStrategy.pay(500, "CARD");
        paymentWithoutStrategy.pay(300, "PAYTM");
    }
}


/*With strategy*/

/*strategy factory avoids the open-close breaking
* and allows to follow single responsibility by creating multiple separate impls of it
* */
interface Payment{
    void pay(int amt);
}

@Slf4j
class Card implements Payment{
    @Override
    public void pay(int amt) {
        log.info("paid {} via card", amt);
    }
}

@Slf4j
class Cash implements Payment{
    @Override
    public void pay(int amt) {
        log.info("paid {} via cash", amt);
    }
}


@Slf4j
class Paytm implements Payment{
    @Override
    public void pay(int amt) {
        log.info("paid {} via paytm", amt);
    }
}



class TestWithStrategy{
    public static void main(String[] args) {
        Payment card = new Card();
        card.pay(100);

        Payment cash  = new Cash();
        cash.pay(200);

        Payment paytm = new Paytm();
        paytm.pay(300);
    }
}

