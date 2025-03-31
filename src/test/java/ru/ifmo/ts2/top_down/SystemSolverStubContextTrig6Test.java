package ru.ifmo.ts2.top_down;

import org.springframework.context.annotation.Import;
import ru.ifmo.ts2.config.StubConfig;
import ru.ifmo.ts2.config.StubSaverConfig;
import ru.ifmo.ts2.functions.trig.Cos;
import ru.ifmo.ts2.functions.trig.Cot;
import ru.ifmo.ts2.functions.trig.Sec;
import ru.ifmo.ts2.functions.trig.Sin;
import ru.ifmo.ts2.functions.trig.Tan;

@Import(value = {
        StubConfig.class,
        StubSaverConfig.class,
        Cos.class,
        Cot.class,
        Sec.class,
        Sin.class,
        Tan.class
})
class SystemSolverStubContextTrig6Test extends SystemSolverStubContextTrigBaseTest {

}