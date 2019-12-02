package com.paci.mock.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paci.mock.web.rest.TestUtil;

public class PaciTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paci.class);
        Paci paci1 = new Paci();
        paci1.setId("id1");
        Paci paci2 = new Paci();
        paci2.setId(paci1.getId());
        assertThat(paci1).isEqualTo(paci2);
        paci2.setId("id2");
        assertThat(paci1).isNotEqualTo(paci2);
        paci1.setId(null);
        assertThat(paci1).isNotEqualTo(paci2);
    }
}
