package org.acme.timer;
import org.slf4j.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.kubernetes.KubernetesConstants;


import io.fabric8.kubernetes.api.model.Pod;

public class KubernertesProcessor implements Processor {
    
    static Logger LOG = LoggerFactory.getLogger(KubernertesProcessor.class);
    
    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, String> labels = new HashMap<>();
        labels.put("app.kubernetes.io/name", "sample-quarkus");
        exchange.getIn().setHeader(KubernetesConstants.KUBERNETES_PODS_LABELS, labels);
        //LOG.info("Got event with configmap name: " + pod.getMetadata().getName() + " and action " + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
    }

}
