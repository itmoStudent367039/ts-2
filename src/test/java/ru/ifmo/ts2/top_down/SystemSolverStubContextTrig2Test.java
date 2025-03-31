package ru.ifmo.ts2.top_down;

import org.springframework.context.annotation.Import;
import ru.ifmo.ts2.config.StubConfig;
import ru.ifmo.ts2.config.StubSaverConfig;
import ru.ifmo.ts2.config.trig.CosConfig;
import ru.ifmo.ts2.config.trig.CotConfig;
import ru.ifmo.ts2.config.trig.SecConfig;
import ru.ifmo.ts2.config.trig.SinConfig;
import ru.ifmo.ts2.config.trig.TanConfig;
import ru.ifmo.ts2.functions.trig.Cos;
import ru.ifmo.ts2.functions.trig.Tan;

@Import(value = {
        StubConfig.class,
        StubSaverConfig.class,
        Cos.class,
        CotConfig.class,
        SecConfig.class,
        SinConfig.class,
        TanConfig.class
})
class SystemSolverStubContextTrig2Test extends SystemSolverStubContextTrigBaseTest {

}