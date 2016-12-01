package edu.casetools.dcase.m2uppaal.mnta;

import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2uppaal.data.MData;
import edu.casetools.dcase.m2uppaal.data.elements.BoundedOperator.BOP_TYPE;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Automaton;
import edu.casetools.dcase.m2uppaal.mnta.templates.EventGenerator;
import edu.casetools.dcase.m2uppaal.mnta.templates.MTemplateGenerator;
import edu.casetools.dcase.m2uppaal.mnta.templates.NTRGenerator;
import edu.casetools.dcase.m2uppaal.mnta.templates.SAPGenerator;
import edu.casetools.dcase.m2uppaal.mnta.templates.SIPGenerator;
import edu.casetools.dcase.m2uppaal.mnta.templates.STRGenerator;
import edu.casetools.dcase.m2uppaal.mnta.templates.WAPGenerator;
import edu.casetools.dcase.m2uppaal.mnta.templates.WIPGenerator;

public class TemplateGenerationManager {
    private MData systemData;

    public TemplateGenerationManager(MData systemData) {
	this.systemData = systemData;
    }

    public List<Automaton> generateTemplates() {
	List<Automaton> list = new ArrayList<>();

	list.addAll(new MTemplateGenerator(systemData).generate());
	if (!systemData.getEvents().isEmpty()) {
	    list.addAll(new EventGenerator(systemData).generate());
	}
	if (systemData.getBopNumber(BOP_TYPE.STRONG_IMMEDIATE_PAST) > 0) {
	    list.addAll(new SIPGenerator(systemData).generate());
	}
	if (systemData.getBopNumber(BOP_TYPE.WEAK_IMMEDIATE_PAST) > 0) {
	    list.addAll(new WIPGenerator(systemData).generate());
	}
	if (systemData.getBopNumber(BOP_TYPE.STRONG_ABSOLUTE_PAST) > 0) {
	    list.addAll(new SAPGenerator(systemData).generate());
	}
	if (systemData.getBopNumber(BOP_TYPE.WEAK_ABSOLUTE_PAST) > 0) {
	    list.addAll(new WAPGenerator(systemData).generate());
	}
	if (!systemData.getStrs().isEmpty()) {
	    list.addAll(new STRGenerator(systemData).generate());
	}
	if (!systemData.getNtrs().isEmpty()) {
	    list.addAll(new NTRGenerator(systemData).generate());
	}

	return list;
    }

}
