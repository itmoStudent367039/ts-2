package ru.ifmo.ts2.top_down;

import org.springframework.context.annotation.Import;
import ru.ifmo.ts2.config.StubConfig;
import ru.ifmo.ts2.config.StubSaverConfig;
import ru.ifmo.ts2.config.trig.CosConfig;
import ru.ifmo.ts2.config.trig.CotConfig;
import ru.ifmo.ts2.config.trig.SecConfig;
import ru.ifmo.ts2.config.trig.SinConfig;
import ru.ifmo.ts2.config.trig.TanConfig;

@Import(value = {
        StubConfig.class,
        StubSaverConfig.class,
        CosConfig.class,
        CotConfig.class,
        SecConfig.class,
        SinConfig.class,
        TanConfig.class
})
class SystemSolverStubContextTrig1Test extends SystemSolverStubContextTrigBaseTest {

}