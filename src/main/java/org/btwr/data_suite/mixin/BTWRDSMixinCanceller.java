package org.btwr.data_suite.mixin;

import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;

public class BTWRDSMixinCanceller implements MixinCanceller {
    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        if (mixinClassName.equals("com.bwt.mixin.animals.WolfEntityMixin")) {
            return true;
        }

        return false;
    }
}