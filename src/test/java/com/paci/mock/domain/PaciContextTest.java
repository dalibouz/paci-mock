package com.paci.mock.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paci.mock.web.rest.TestUtil;

public class PaciContextTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaciContext.class);
        PaciContext paciContext1 = new PaciContext();
        paciContext1.setId("id1");
        PaciContext paciContext2 = new PaciContext();
        paciContext2.setId(paciContext1.getId());
        assertThat(paciContext1).isEqualTo(paciContext2);
        paciContext2.setId("id2");
        assertThat(paciContext1).isNotEqualTo(paciContext2);
        paciContext1.setId(null);
        assertThat(paciContext1).isNotEqualTo(paciContext2);
    }
}
