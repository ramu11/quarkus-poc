package org.acme;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kubernetes.KubernetesConstants;

import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;

public class CamelRouteBuilder extends RouteBuilder {
    
    private static String HOST = "https://api.kakarlatest.lab.upshift.rdu2.redhat.com:6443";
    private static String AUTH_TOKEN = "dzXXUy0ZNgcuqNCyTcnZD4b1L_hcEa3ZRl1GNd86W3M";

    public void configure() {
        
        //Read pods by pod labels
     /*  from("timer:foo?period=10000&repeatCount=1")
       .process(new Processor() {

           @Override
           public void process(Exchange exchange) throws Exception {
               Map<String, String> labels = new HashMap<>();
               labels.put("app.kubernetes.io/name", "sample-quarkus");
               exchange.getIn().setHeader(KubernetesConstants.KUBERNETES_PODS_LABELS, labels);
           }
       })
        .toF("kubernetes-pods://%s?oauthToken=%s&namespace=quarkus-ocp4&operation=listPodsByLabels", HOST, AUTH_TOKEN).
        to("log:result"); */
        
       
       //kubernetes pod consumer 
     /*  fromF("kubernetes-pods://%s?oauthToken=%snamespace=quarkus-ocp4&resourceName=sample-quarkus", HOST, AUTH_TOKEN)
        .process(new KubernertesProcessor() {
                    
            @Override
            public void process(Exchange exchange) throws Exception {
            Message in = exchange.getIn();
            Pod pod = exchange.getIn().getBody(Pod.class);
            log.info("Got event with configmap name: " + pod.getMetadata().getName() + " and action " + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
            }
        });*/
        
        //Read nodes by labels
        /*from("timer:foo?period=10000&repeatCount=1")
        .process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
                Map<String, String> labels = new HashMap<>();
                labels.put("kubernetes.io/hostname", "master-0.kakarlatest.lab.upshift.rdu2.redhat.com");
                labels.put("kubernetes.io/hostname", "worker-1.kakarlatest.lab.upshift.rdu2.redhat.com");
                exchange.getIn().setHeader(KubernetesConstants.KUBERNETES_NODES_LABELS, labels);
            }
        })
        .toF("kubernetes-nodes://%s?oauthToken=%s&namespace=quarkus-ocp4&operation=listNodesByLabels", HOST, AUTH_TOKEN).
        to("log:result");   
        */
        
        //kubernetes node consumer 
        fromF("kubernetes-nodes://%s?oauthToken=%s&resourceName=worker-1.kakarlatest.lab.upshift.rdu2.redhat.com", HOST, AUTH_TOKEN)
        .process(new Processor() {
                    
            @Override
            public void process(Exchange exchange) throws Exception {
                Message in = exchange.getIn();
                Node node = exchange.getIn().getBody(Node.class);
                log.info("Got event with configmap name: " + node.getMetadata().getName() + " and action " + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
            }
        });
        
        
       //kubernetes services consumer
      /*  fromF("kubernetes-services://%s?oauthToken=%s&resourceName=test", HOST, AUTH_TOKEN)
        .process(new Processor() {
                    
            @Override
            public void process(Exchange exchange) throws Exception {
                Message in = exchange.getIn();
                Service service = exchange.getIn().getBody(Service.class);
                log.info("Got event with configmap name: " + service.getMetadata().getName() + " and action " + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
            }
        });*/
         
       //kubernetes deployments
       /* fromF("kubernetes-deployments://%s?oauthToken=%s&namespace=quarkus-test1&resourceName=code-with-quarkus", HOST, AUTH_TOKEN)
        .process(new Processor() {
                    
            @Override
            public void process(Exchange exchange) throws Exception {
                Message in = exchange.getIn();
                Deployment dp = exchange.getIn().getBody(Deployment.class);
                log.info("Got event with configmap name: " + dp.getMetadata().getName() + " and action " + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
            }
        });*/
        
    }
}