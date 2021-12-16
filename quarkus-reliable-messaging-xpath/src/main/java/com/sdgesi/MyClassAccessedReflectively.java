package com.sdgesi;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
class MyClassAccessedReflectively {
}

@RegisterForReflection(
    targets = {
        com.sdgesi.FlakyException.class,
        java.lang.Exception.class

        
    }
)
class ReflectionRegistrations {
}
