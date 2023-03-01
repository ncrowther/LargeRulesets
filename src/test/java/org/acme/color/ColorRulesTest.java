/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme.color;

import javax.inject.Inject;

import org.acme.ColorDecider;
import org.drools.core.common.InternalAgenda;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ColorRulesTest {

    @Inject
    KieRuntimeBuilder ruleRuntime;

    @Test
    public void testRedGreenGreenRule() {

        assertNotNull(ruleRuntime);

        ColorDecider colorDecider = new ColorDecider("Red", "Green", "Green");

        KieSession ksession = ruleRuntime.newKieSession();
        ((InternalAgenda) ksession.getAgenda()).activateRuleFlowGroup("R1_3_1");
        ksession.insert(colorDecider);
        ksession.fireAllRules();

        ksession.dispose();

        assertEquals("Red", colorDecider.getColor());
    }

}
