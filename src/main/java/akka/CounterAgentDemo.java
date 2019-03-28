package akka;

import akka.actor.*;
import akka.agent.Agent;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.Mapper;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Future;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

public class CounterAgentDemo {

    static CountDownLatch latch = new CountDownLatch(10);
    static Agent<Integer> countAgent = Agent.create(0, ExecutionContexts.global());
    static ConcurrentLinkedQueue<Future<Integer>> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws InterruptedException, TimeoutException {
        ActorSystem system = ActorSystem.create("inbox", ConfigFactory.load("akka.conf"));
        ActorRef[] actorRefs = new ActorRef[10];
        for (int i = 0; i < 10; i++) {
            actorRefs[i] = system.actorOf(Props.create(AgentTest.class), "AgentTest" + i);
        }
        Inbox inbox = Inbox.create(system);
        for (ActorRef ref : actorRefs) {
            inbox.send(ref, 1);
            inbox.watch(ref);
        }
        System.out.println("countAgent 1:" + countAgent.get());
    }


    static class AgentTest extends UntypedActor {
        private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        @Override
        public void onReceive(Object o) throws Throwable {
            if (o instanceof Integer) {
                for (int i = 0; i < 10000; i++) {
                    Future<Integer> future = countAgent.alter(new Mapper<Integer, Integer>() {
                        @Override
                        public Integer apply(Integer parameter) {
                            return parameter + 1;
                        }
                    });
                    queue.add(future);
                }
                getContext().stop(getSelf());//完成任务，关闭自己 }else{ unhandled(o); } }
            }
        }
    }
}
