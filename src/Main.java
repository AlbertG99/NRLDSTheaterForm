import java.awt.Font;
import java.awt.GridBagConstraints;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import jotform.JotForm;
import json.JSONException;
import json.JSONObject;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Main
{
	public Main() {}

	public static void main(String[] args) throws JSONException, ParseException, InterruptedException, IOException
	{
		//	    propertySearch(getForm(), 73411121195143L, "expireDate");
		//	    setProperty(getForm(), 73411121195143L, "expireDate", "2018-03-30 23:59");

		//	    setProperty(getForm(), 73411121195143L, "status", "DISABLED");
		//	    setProperty(getForm(), 73411121195143L, "status", "ENABLED");
		//	    setProperty(getForm(), 73411121195143L, "expireDate", "2018-03-30 23:59");
		//	    System.exit(0);
		try
		{
			runProgram();
		}
		catch (StringIndexOutOfBoundsException|NegativeArraySizeException e)
		{
			showInputError();
		} catch (Exception e) {
			showOtherError();
		}
	}

	public static void runProgram() throws JSONException, ParseException, InterruptedException, IOException {
		JTextArea textArea = displayOutput("GETTING PARAMETERS:");

		updateOutput(textArea, "General parameters...");
		ArrayList<String> generalInputs = getGeneralInput();
		updateOutput(textArea, "Class #1 parameters...");
		ArrayList<Object> class1Inputs = getClassInput(1);
		updateOutput(textArea, "Class #2 parameters...");
		ArrayList<Object> class2Inputs = getClassInput(2);

		long startTime = System.currentTimeMillis();

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat jotFormDateFormat = new SimpleDateFormat("yyyy-MM-dd");


		String vacaStart = (String)generalInputs.get(4);

		String vacaStartText = dateToWords(vacaStart);
		String vacaStart1 = vacaStart.substring(0, 3) + (Integer.parseInt(vacaStart.substring(3, 5)) - 1) + vacaStart.substring(5);
		String vacaStart1Text = dateToWords(vacaStart1);

		Date vacaStart1Date = dateFormat.parse(vacaStart1);
		String vacStart = "custom_" + jotFormDateFormat.format(vacaStart1Date);


		String vacaEnd = (String)generalInputs.get(5);

		String vacaEndText = dateToWords(vacaEnd);
		String vacaEnd1 = vacaEnd.substring(0, 3) + (Integer.parseInt(vacaEnd.substring(3, 5)) + 1) + vacaEnd.substring(5);
		String vacaEnd1Text = dateToWords(vacaEnd1);

		Date vacaEnd1Date = dateFormat.parse(vacaEnd1);
		String vacEnd = "custom_" + jotFormDateFormat.format(vacaEnd1Date);

		String vacaOptions = generateVacation(vacaStart, vacaEnd) + "|Check if you don't know your plans on the days not selected.";


		String concert = (String)generalInputs.get(3);
		boolean drFriday = ((String)generalInputs.get(10)).equals("Fri/Sat");
		//System.out.println(drFriday);
		//System.exit(0);

		String concertText = dateToWords(concert);
		Date concertDate = dateFormat.parse(concert);

		Date dressRehearsalDate = new Date(concertDate.getTime() - 2L);
		String dressRehearsal = dateFormat.format(dressRehearsalDate);
		dressRehearsalDate = dateFormat.parse(dressRehearsal);
		String dressRehearsalText = dateToWords(dressRehearsal);
		
		Date dressRehearsal2Date = new Date(concertDate.getTime() - 2L);
		String dressRehearsal2 = dateFormat.format(dressRehearsal2Date);
		dressRehearsal2Date = dateFormat.parse(dressRehearsal2);
		String dressRehearsal2Text = dateToWords(dressRehearsal2);
		if (drFriday) {
			dressRehearsal2Date = new Date(dressRehearsalDate.getTime() - 2L);
			dressRehearsal2 = dateFormat.format(dressRehearsal2Date);
			dressRehearsal2Date = dateFormat.parse(dressRehearsal2);
			dressRehearsal2Text = dateToWords(dressRehearsal2);
		}
		//System.out.println(concertDate + "" + dressRehearsalDate + "" + dressRehearsal2Date);
		//System.exit(0);

		Date lastRehearsalDate = new Date(dressRehearsal2Date.getTime() - 2L);
		String lastRehearsal = dateFormat.format(lastRehearsalDate);
		String lastRehearsalText = dateToWords(lastRehearsal);
		String lastDay = "custom_" + jotFormDateFormat.format(lastRehearsalDate);

		Date vacaStartDate = new SimpleDateFormat("MM/dd/yyyy").parse(vacaStart);
		Date vacaEndDate = new SimpleDateFormat("MM/dd/yyyy").parse(vacaEnd);


		String ninaStart = (String)generalInputs.get(0);

		String ninaStartText = dateToWords(ninaStart);
		Date ninaStartDate = dateFormat.parse(ninaStart);
		String firstDay = "custom_" + jotFormDateFormat.format(ninaStartDate);
		String ninaEnd = (String)generalInputs.get(1);

		String ninaEndText = dateToWords(ninaEnd);
		Date ninaEndDate = dateFormat.parse(ninaEnd);
		String ninaDateRange = "";
		String ninaDateRangeFormatted = "";
		if (ninaEndDate.before(vacaStartDate)) {
			ninaDateRange = "from " + ninaStartText + " to " + ninaEndText;
			ninaDateRangeFormatted = "from <strong>" + ninaStartText + " to " + ninaEndText + "</strong>";
		}
		else if (ninaEndDate.before(vacaEndDate)) {
			ninaDateRange = "from " + ninaStartText + " to " + vacaStart1Text;
			ninaDateRangeFormatted = "from <strong>" + ninaStartText + " to " + vacaStart1Text + "</strong>";
		}
		else {
			ninaDateRange = "from " + ninaStartText + " to " + vacaStart1Text + " and from " + vacaEnd1Text + " to " + ninaEndText;
			ninaDateRangeFormatted = "from <strong>" + ninaStartText + " to " + vacaStart1Text + "</strong> and from <strong>" + vacaEnd1Text + " to " + ninaEndText + "</strong>";
		}


		String mishaStart = (String)generalInputs.get(2);

		String mishaStartText = dateToWords(mishaStart);
		String mishaEnd = lastRehearsal;
		String mishaEndText = dateToWords(mishaEnd);
		Date mishaStartDate = new SimpleDateFormat("MM/dd/yyyy").parse(mishaStart);
		String DMSR = "custom_" + jotFormDateFormat.format(mishaStartDate);

		String mishaDateRange = "";
		String mishaDateRangeFormatted = "";
		if (mishaStartDate.before(vacaStartDate)) {
			mishaDateRange = "from " + mishaStartText + " to " + vacaStart1Text + " and from " + vacaEnd1Text + " to " + mishaEndText;
			mishaDateRangeFormatted = "from <strong>" + mishaStartText + " to " + vacaStart1Text + "</strong> and from <strong>" + vacaEnd1Text + " to " + mishaEndText + "</strong>";
		}
		else if (mishaStartDate.before(vacaEndDate)) {
			mishaDateRange = "from " + vacaEnd1Text + " to " + mishaEndText;
			mishaDateRangeFormatted = "from <strong>" + vacaEnd1Text + " to " + mishaEndText + "</strong>";
		}
		else {
			mishaDateRange = "from " + mishaStartText + " to " + mishaEndText;
			mishaDateRangeFormatted = "from <strong>" + mishaStartText + " to " + mishaEndText + "</strong>";
		}

		String fullRange = "from " + ninaStartText + " to " + vacaStart1Text + " and from " + vacaEnd1Text + " to " + lastRehearsalText;


		String formDeadline = (String)generalInputs.get(6);

		String formDeadlineText = dateToWords(formDeadline);
		Date formDeadlineDate = dateFormat.parse(formDeadline);
		String formDeadlineProperty = jotFormDateFormat.format(formDeadlineDate) + " 23:59";


		int[] ninaSlotChoices1 = (int[])class1Inputs.get(1);
		int ninaClassSlot1 = ((Integer)class1Inputs.get(2)).intValue();
		int[] ninaSlotChoices2 = (int[])class2Inputs.get(1);
		int ninaClassSlot2 = ((Integer)class2Inputs.get(2)).intValue();
		int[][] ninaSlotChoices = { ninaSlotChoices1, ninaSlotChoices2 };
		int[] ninaClassSlots = { ninaClassSlot1, ninaClassSlot2 };
		String[] ninaSlotsArray = { "Mon from 4 or 4:30 pm for 60-90 min", "Mon from 5:30 or 6 pm for 60-90 min", "Tue from 4 or 4:30 pm for 60-90 min", "Tue from 5:30 or 6 pm for 60-90 min", "Wed from 3:30 or 4 pm for 60-90 min", "Wed from 4 or 4:30 pm for 60-90 min", "Wed from 5:30 or 6 pm for 60-90 min", "Thu from 3:30 or 4 pm for 60-90 min", "Thu from 4 or 4:30 pm for 60-90 min", "Thu from 5:30 or 6 pm for 60-90 min", "Fri from 3:30 or 4 pm for 60-90 min", "Fri from 4 or 4:30 pm for 60-90 min", "Fri from 5:30 or 6 pm for 60-90 min", "Sat from 10 am for 60-90 min", "Sat from 11:30 am or 12 pm for 60-90 min", "Sun from 10 am for 60-90 min" };
		System.out.println(ninaSlotsArray.length);
		long[] ninaQsBM = { 88L, 92L, 89L, 93L, 406L, 352L, 407L, 408L, 414L, 410L, 411L, 412L, 413L, 0, 98L, 0 };
		long[] ninaQsSm = { 88L, 92L, 89L, 93L, 418L, 352L, 419L, 420L, 421L, 422L, 423L, 424L, 425L, 0, 98L, 0 };
		String[] nonOmitSlots = { (String)class1Inputs.get(3), (String)class2Inputs.get(3) };


		boolean mishaWorking = false;
		if (((String)generalInputs.get(7)).equals("Yes")) {
			mishaWorking = true;
		}
		boolean mishaFriday = false;
		if (((String)generalInputs.get(9)).equals("Yes")) {
			mishaFriday = true;
		}
		String mishaSlots = "Mon 6/6:30|Tue 6/6:30|Wed 6/6:30|Thu 6/6:30|Fri 6/6:30|Sat 11:00 am|Sat 3:30 or 4:00|Sun 11:00 am|Sun 3:30 or 4:00";
		if (mishaFriday) {
			mishaSlots = mishaSlots.replace("Fri 6/6:30", "Fri 6:15/6:30");
		}
		if (!mishaWorking) {
			mishaSlots = mishaSlots.replace("6/6:30", "5/5:30");
		}
		String SEexamples = "";
		if (mishaWorking) {
			SEexamples = "<p>Example 1: Your EVENT is <strong><span style=\"color: #0000ff;\">from 4 pm to 5:30 pm on a Tuesday after " + ninaEndText + "</span></strong>. You do not need to log this date as an SE. Why? Because Nina has already finished readings/line tests by " + ninaEndText + ", and Misha starts rehearsing at 6/6:30 pm on Tuesdays. Thus, your Tue 4-5:30 cannot possibly be scheduled so do not log it as SE (please, notice - <span style=\"color: #0000ff;\">You may still be scheduled for 6 pm that day <strong>unless you log this date as SE</strong></span>).</p>\n<p>Example 2: Your event is on a <span style=\"color: #0000ff;\"><strong>Saturday from 4 to 8 pm before " + mishaStartText + "</strong></span>. Do NOT log this date/time slot as SE. WHY? On Saturdays Nina finishes readings by 1:30 pm (see section III), and Misha did not start rehearsing yet. Thus, this slot cannot possibly be scheduled.</p>\n<p>Example 3: Your event is on a <strong><span style=\"color: #0000ff;\">Monday, at 6:30 pm</span></strong>. If your IMPOSSIBLE weekly conflict with Misha is <span style=\"color: #ff0000;\">Mon 6 pm</span> AND your IMPOSSIBLE weekly conflict with Nina is <span style=\"color: #ff0000;\">Mon, 5:30/6 pm</span> (not Mon 4/4:30), you do NOT have to log this date as SE. WHY? Your readings/rehearsals <strong><span style=\"color: #ff0000;\">cannot</span></strong> be scheduled on your <strong><span style=\"color: #ff0000;\">IMPOSSIBLE Mon/6 pm</span></strong> anyway (please, notice - you may still have a reading with Nina on that Monday at 4/4:30 if the date is before or on " + ninaEndText + ").</p>";
		}
		else {
			SEexamples = "<p>Example 1: Your event is on a <span style=\"color: #0000ff;\"><strong>Saturday from 4 to 8 pm before " + mishaStartText + "</strong></span>. Do NOT log this date/time slot as SE. WHY? On Saturdays Nina finishes readings by 1:30 pm (see section III), and Misha did not start rehearsing yet. Thus, this slot cannot possibly be scheduled.</p>\n<p>Example 2: Your event is on a <span style=\"color: #0000ff;\"><strong>Monday, at 4 pm</strong></span>. If your IMPOSSIBLE weekly conflict with Misha is <span style=\"color: #ff0000;\">Mon 4:45pm/5:15pm</span> AND your IMPOSSIBLE weekly conflict with Nina is <span style=\"color: #ff0000;\">Mon, 4 or 4:30 pm</span> (not Mon 5:30/6), you do NOT have to log this date as SE. WHY? Your readings/rehearsals <span style=\"color: #ff0000;\"><strong>cannot</strong></span> be scheduled on your <span style=\"color: #ff0000;\"><strong>IMPOSSIBLE Mon/4 pm</strong></span> anyway (please, notice - you may still have a reading with Nina on that Monday at 6 pm if the date is before or on " + ninaEndText + ").</p>";
		}

		String[] classNames = { (String)class1Inputs.get(0), (String)class2Inputs.get(0) };

		//System.exit(0); // EXITS PROGRAM BEFORE DEPLOYING CHANGES
		
		JotForm form = getForm();

		long[] formIDs = { 70678254459165L, 70678561692165L, 72127926906159L, 72128695306157L };

		for (int i = 0; i < 2; i++) {
			String toHideList = "0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15";
			String ninaSlots = "";
			System.out.println(arrayToString(ninaSlotChoices[0]));
			for (int slotNum : ninaSlotChoices[i]) {
				if (slotNum != ninaClassSlots[i]) {
					ninaSlots = ninaSlots + "|" + ninaSlotsArray[slotNum];
				}
				if (ninaQsBM[slotNum] != 0L)
				{

					toHideList = toHideList.replace("|" + Integer.toString(slotNum) + "|", "||"); }
			}
			ninaSlots = ninaSlots.substring(1);

			String className = classNames[i];

			updateOutput(textArea, "SMALL for " + className.toUpperCase() + ":");
			long formID = formIDs[(0 + 2 * i)];
			updateOutput(textArea, "I. General information...");
			String title = "Theater Form for Concert on " + dateToFullWords(concert) + " - Small Role";
			setProperty(form, formID, "pagetitle", title);
			setProperty(form, formID, "status", "DISABLED");
			setProperty(form, formID, "status", "ENABLED");
			setProperty(form, formID, "expireDate", formDeadlineProperty);
			saveQuestionProps("text", title, form, formID, 1L);
			saveQuestionProps("subHeader", className, form, formID, 1L);
			saveQuestionProps("text", title, form, formID, 417L);
			saveQuestionProps("subHeader", className, form, formID, 417L);

			String introText = "<p class=\"p1\"><span style=\"font-size: 10pt;\"><span class=\"s1\" style=\"color: #ff0000;\">*</span><span class=\"s2\"> indicates a required question. You will not be able to submit the form without answering the question.</span></span></p><p class=\"p1\"><span class=\"s2\" style=\"font-size: 10pt;\"><strong>For help</strong>: Move your mouse&nbsp;over a question and read the information in the popup.</span></p><p class=\"p1\"><span class=\"s2\" style=\"font-size: 10pt;\">Please note: Every actor will be busy the whole day on <strong>Saturday, " + dateToFullWords(dressRehearsal) + "</strong> and on <strong>Sunday, " + dateToFullWords(concert) + "</strong>.</span></p>";
			if (drFriday) {
				introText = "<p class=\"p1\"><span style=\"font-size: 10pt;\"><span class=\"s1\" style=\"color: #ff0000;\">*</span><span class=\"s2\"> indicates a required question. You will not be able to submit the form without answering the question.</span></span></p><p class=\"p1\"><span class=\"s2\" style=\"font-size: 10pt;\"><strong>For help</strong>: Move your mouse&nbsp;over a question and read the information in the popup.</span></p><p class=\"p1\"><span class=\"s2\" style=\"font-size: 10pt;\">Please note: Every actor will be busy after 4pm on <strong>Friday, " + dateToFullWords(dressRehearsal2) + "</strong>, after 1:30pm on <strong>Saturday, " + dateToFullWords(dressRehearsal) + "</strong>, and the whole day on <strong>Sunday, " + dateToFullWords(concert) + "</strong>.</span></p>";
			}
			saveQuestionProps("text", introText, form, formID, 11L);

			String inAdditionText = "<p>In addition, every actor will have a dress rehearsal on stage on Saturday, " + dateToFullWords(dressRehearsal) + ".</p>";
			if (drFriday) {
				inAdditionText = "<p>In addition, every actor will have a dress rehearsal on stage on Friday, " + dateToFullWords(dressRehearsal2) + " after 4pm AND/OR Saturday, " + dateToFullWords(dressRehearsal) + " after 1:30pm.</p>";
			}
			saveQuestionProps("text", inAdditionText, form, formID, 410L);

			saveQuestionProps("text", "<p><span style=\"font-size:12pt;\">We will try to satisfy your request but cannot promise.</span></p>\n<p><span style=\"font-size:12pt;\">Keep in mind that you <strong>will be able to make changes</strong> in this form after submission until " + formDeadlineText + ". When you click Submit at the very end, you will get an automatic email with instructions on how to edit your submitted form.</span></p>", form, formID, 10L);

			updateOutput(textArea, "II. Rehearsals with Misha...");
			saveQuestionProps("subHeader", mishaDateRange, form, formID, 7L);
			saveQuestionProps("subHeader", mishaDateRange, form, formID, 355L);

			String mishaText = "<p><span style=\"font-size: 12pt;\">Misha's rehearsals usually take approximately&nbsp;<strong>2 hours for younger actors</strong>&nbsp;and&nbsp;<strong>2 to 2.5 hours for older actors</strong>&nbsp;(and longer in very rare cases).</span></p>\n<p><span style=\"font-size: 12pt;\">For Misha's rehearsals, you can select up to&nbsp;<strong>3 weekly conflicts</strong>&nbsp;(1 impossible, 2 undesirable)&nbsp;<span style=\"color: #0000ff;\">" + mishaDateRangeFormatted + "</span>.</span></p>\n<p><span style=\"font-size: 12pt;\">If you have ONE&nbsp;<strong>day/time</strong>&nbsp;out of the list below that is absolutely IMPOSSIBLE for rehearsals with Misha, you can log it here.</span></p>";
			saveQuestionProps("text", mishaText, form, formID, 12L);

			saveQuestionProps("options", mishaSlots, form, formID, 13L);
			saveQuestionProps("options", mishaSlots, form, formID, 14L);
			saveQuestionProps("options", mishaSlots, form, formID, 15L);

			saveQuestionProps("text", "<p><span style=\"font-size:12pt;\">You cannot choose more than <strong>1 IMPOSSIBLE weekly conflict</strong>, but now you can choose <strong>2 UNDESIRABLE weekly conflicts</strong> <span style=\"color:#0000ff;\">" + mishaDateRangeFormatted + "</span>.</span></p>\n<p><span style=\"font-size:12pt;\">If you have <strong>one or two day/time slots on which you prefer not to rehearse with Misha</strong>, you can enter them below. We will try hard not to schedule these slots.</span></p>", form, formID, 18L);
			saveQuestionProps("text", "<p><span style=\"font-size:12pt;\">You have selected no IMPOSSIBLE weekly conflicts, so you can select <strong>up to three</strong> UNDESIRABLE weekly conflicts <span style=\"color:#0000ff;\">" + mishaDateRangeFormatted + "</span>.</span></p>\n<p><span style=\"font-size:12pt;\">If you have <strong>one, two, or three day/time slots on which you prefer not to rehearse with Misha</strong>, you can enter them below. We will try hard not to schedule these slots.</span></p>", form, formID, 19L);

			updateOutput(textArea, "III. Working on lines with Nina...");
			saveQuestionProps("subHeader", ninaDateRange, form, formID, 17L);

			for (int slotNum = 0; slotNum < ninaQsSm.length; slotNum++)
				if (ninaQsSm[slotNum] != 0L)
				{

					saveQuestionProps("text", "", form, formID, ninaQsSm[slotNum]);
					saveQuestionProps("hidden", "Yes", form, formID, ninaQsSm[slotNum]);
				}
			for (int slotNum : ninaSlotChoices[i])
				if (ninaQsSm[slotNum] != 0L)
				{

					if (slotNum == ninaClassSlots[i]) {
						saveQuestionProps("options", nonOmitSlots[i], form, formID, ninaQsSm[slotNum]);
					}
					saveQuestionProps("text", ninaSlotsArray[slotNum], form, formID, ninaQsSm[slotNum]);
					saveQuestionProps("hidden", "No", form, formID, ninaQsSm[slotNum]);
				}
			saveQuestionProps("defaultValue", toHideList, form, formID, 426L);
			saveQuestionProps("options", ninaSlots, form, formID, 61L);
			saveQuestionProps("options", ninaSlots, form, formID, 63L);
			saveQuestionProps("options", ninaSlots, form, formID, 65L);

			saveQuestionProps("text", "<p><span style=\"font-size:10pt;\">Nina works on lines <strong>TWICE</strong>: <strong>first a reading</strong> and then the <strong>test on lines</strong> (may be more for a very big role).</span></p>\n<p><span style=\"font-size:10pt;color:#ff0000;\"><strong>Please notice: Misha's and Nina's schedules are completely separate. Even if you have logged a weekly conflict in Misha's section II, it does not carry over to Nina's section III and vice versa.</strong></span></p>\n<p><span style=\"font-size:10pt;color:#ff0000;\">For example, if Monday evening is your conflict for Nina's readings, you must log it below even if you logged Monday evening as a conflict for Misha's rehearsals.</span></p>\n<p><span style=\"font-size:10pt;\">Select weekly conflicts <span style=\"color:#0000ff;\">" + ninaDateRangeFormatted + "</span>. Choose up to <strong>one day/time slot</strong> that is IMPOSSIBLE for working with Nina. You can choose <strong>second and third day/time slots</strong> that are UNDESIRABLE for working with Nina.</span></p>", form, formID, 20L);
			saveQuestionProps("text", "<p><span style=\"font-size:10pt;\">You cannot choose more than <strong>1 IMPOSSIBLE weekly conflict</strong>, but now you can choose <strong>2 UNDESIRABLE weekly conflicts</strong> <span style=\"color:#0000ff;\">" + ninaDateRangeFormatted + "</span><strong>.</strong></span></p>\n<p><span style=\"color:#0000ff;\"><strong>If you change your IMPOSSIBLE weekly conflict, your UNDESIRABLE weekly conflict(s) will be erased, so make sure to re-select your UNDESIRABLE weekly conflict.</strong></span></p>", form, formID, 62L);
			saveQuestionProps("text", "<p><span style=\"font-size:10pt;\">You have selected no IMPOSSIBLE weekly conflicts, so you can select <strong>up to three</strong> UNDESIRABLE weekly conflicts <span style=\"color:#0000ff;\">" + ninaDateRangeFormatted + "</span>.</span></p>\n<p><span style=\"color:#0000ff;\"><strong>If you change your IMPOSSIBLE weekly conflict, your UNDESIRABLE weekly conflict(s) will be erased, so make sure to re-select your UNDESIRABLE weekly conflict.</strong></span></p>", form, formID, 64L);

			updateOutput(textArea, "IV. Vacation...");
			saveQuestionProps("text", "<p><span style=\"font-size:12pt;\">You <strong>do not</strong> have to rehearse on vacation, but if your stage group is in the area during vacation we could schedule some rehearsals.</span></p>\n<p><span style=\"font-size:12pt;\">If you leave for vacation a day earlier or return a day later, please, <strong>log these days as SPECIAL EXCEPTIONS in section V</strong>.</span></p>\n<p><span style=\"font-size:12pt;\">If you don't know your whole vacation plans yet, please enter what you already know below. You can submit the form without complete information today, but you must finalize this section by <strong>" + formDeadlineText + "</strong>.</span></p>", form, formID, 27L);

			saveQuestionProps("options", vacaOptions, form, formID, 50L);

			updateOutput(textArea, "V. Special exceptions...");
			saveQuestionProps("subHeader", fullRange, form, formID, 30L);
			saveQuestionProps("subHeader", fullRange, form, formID, 240L);

			String SEintro = "<p><strong>If you have an important event on the day/time of the week which you claimed above as available (or undesirable) for work with NINA (<span style=\"color:#0000ff;\">" + ninaDateRange + "</span>) or with MISHA (<span style=\"color:#0000ff;\">" + mishaDateRange + "</span>), you can log this date as a Special Exception (SE).</strong></p>\n<p> </p>\n<p>WARNING: Be attentive so that you <span style=\"color:#ff0000;\">do not waste</span> your Special Exceptions (SE) on <span style=\"color:#ff0000;\">the day/time</span> when your rehearsals and readings <span style=\"color:#ff0000;\">cannot be scheduled</span>.</p>\n<p>1) If you selected <span style=\"color:#ff0000;\">Sunday morning</span> as your <span style=\"color:#ff0000;\">weekly IMPOSSIBLE</span> conflict for <span style=\"color:#ff0000;\">BOTH Nina’s readings</span> (Section III) <span style=\"color:#ff0000;\">and Misha’s rehearsals</span> (Section II), you <span style=\"color:#ff0000;\">do NOT have to log a slot</span> that falls on <span style=\"color:#ff0000;\">Sunday from 10 am to 1:30 pm</span> as your Special Exception.</p>\n<p>2) The rest is more complicated, and we cannot offer a general recipe. You have to make a specific decision about logging a Special Exception for each such case. You may need to see Nina's and Misha's schedule choices in Sections II and III. You will also have to remember that: <strong><span style=\"color:#0000ff;\">Nina reads from " + ninaStartText + " to " + ninaEndText + ", and Misha rehearses from " + mishaStartText + " to " + mishaEndText + "</span></strong>.</p>";
			saveQuestionProps("text", SEintro, form, formID, 359L);

			saveQuestionProps("text", SEexamples, form, formID, 396L);

			String allSEintro = "<p><strong><span style=\"font-size:10pt;\">If you have an important event on the day/time of the week which you claimed above as available (or undesirable) f<strong>or work with NINA (<span style=\"color:#0000ff;\">" + ninaDateRange + "</span>) or with MISHA (<span style=\"color:#0000ff;\">" + mishaDateRange + "</span>)</strong>, you can log this date below.</span></strong></p>\n<p>REMINDER: Be attentive so that you <span style=\"color:#ff0000;\">do not waste</span> your Special Exceptions (SE) on <span style=\"color:#ff0000;\">the day/time</span> when your rehearsals and readings <span style=\"color:#ff0000;\">cannot be scheduled</span>. <span style=\"color:#ff0000;\">See the explanation at the beginning of Section V.</span></p>";
			saveQuestionProps("text", allSEintro, form, formID, 251L);
			saveQuestionProps("text", allSEintro, form, formID, 32L);
			saveQuestionProps("text", allSEintro, form, formID, 252L);
			saveQuestionProps("text", allSEintro, form, formID, 253L);
			saveQuestionProps("text", allSEintro, form, formID, 254L);
			saveQuestionProps("text", allSEintro, form, formID, 255L);
			saveQuestionProps("text", allSEintro, form, formID, 365L);
			saveQuestionProps("text", allSEintro, form, formID, 375L);

			String allUSEintro = "<p><strong>If you have an event on the day/time of the week which you claimed above as available (or undesirable) f<strong>or work with NINA (<span style=\"color:#0000ff;\">" + ninaDateRange + "</span>) or with MISHA (<span style=\"color:#0000ff;\">" + mishaDateRange + "</span>)</strong>, you can log this date below. This date will be scheduled only if absolutely necessary.</strong></p>\n<p><span style=\"font-size:10pt;\">REMINDER: Be attentive so that you </span><span style=\"color:#ff0000;\">do not waste</span><span style=\"font-size:10pt;\"> your Special Exceptions (SE) on </span><span style=\"color:#ff0000;\">the day/time</span><span style=\"font-size:10pt;\"> when your rehearsals and readings </span><span style=\"color:#ff0000;\">cannot be scheduled</span><span style=\"font-size:10pt;\">. <span style=\"color:#ff0000;\">See the explanation at the beginning of Section V.</span></span></p>";
			saveQuestionProps("text", allUSEintro, form, formID, 256L);
			saveQuestionProps("text", allUSEintro, form, formID, 257L);

			String rangeWarning = "<p><strong><span style=\"font-size:10pt;color:#ff0000;\">You have selected a day outside of the available range. The date must be between " + ninaStartText + " and " + lastRehearsalText + ".<strong><span style=\"font-size:10pt;color:#ff0000;\"><strong><span style=\"font-size:10pt;color:#ff0000;\"> Please IGNORE the following prompt/question and clear the date.</span></strong></span></strong></span></strong></p>";
			saveQuestionProps("text", rangeWarning, form, formID, 265L);
			saveQuestionProps("text", rangeWarning, form, formID, 306L);
			saveQuestionProps("text", rangeWarning, form, formID, 270L);
			saveQuestionProps("text", rangeWarning, form, formID, 279L);
			saveQuestionProps("text", rangeWarning, form, formID, 317L);
			saveQuestionProps("text", rangeWarning, form, formID, 288L);
			saveQuestionProps("text", rangeWarning, form, formID, 368L);
			saveQuestionProps("text", rangeWarning, form, formID, 378L);
			saveQuestionProps("text", rangeWarning, form, formID, 326L);
			saveQuestionProps("text", rangeWarning, form, formID, 297L);

			String mishaWarning = "<p><strong><span style=\"font-size:10pt;color:#ff0000;\">Misha will start rehearsing only on " + mishaStartText + ". You don't have to log this date/time as a SE. Please clear the date.</span></strong></p>";
			saveQuestionProps("text", mishaWarning, form, formID, 259L);
			saveQuestionProps("text", mishaWarning, form, formID, 308L);
			saveQuestionProps("text", mishaWarning, form, formID, 272L);
			saveQuestionProps("text", mishaWarning, form, formID, 319L);
			saveQuestionProps("text", mishaWarning, form, formID, 281L);
			saveQuestionProps("text", mishaWarning, form, formID, 328L);
			saveQuestionProps("text", mishaWarning, form, formID, 370L);
			saveQuestionProps("text", mishaWarning, form, formID, 380L);
			saveQuestionProps("text", mishaWarning, form, formID, 290L);
			saveQuestionProps("text", mishaWarning, form, formID, 299L);

			saveQuestionProps("defaultDate", DMSR, form, formID, 428L);
			saveQuestionProps("defaultDate", vacStart, form, formID, 431L);
			saveQuestionProps("defaultDate", vacEnd, form, formID, 432L);
			saveQuestionProps("defaultDate", firstDay, form, formID, 429L);
			saveQuestionProps("defaultDate", lastDay, form, formID, 430L);

			updateOutput(textArea, "VI. Parent volunteers...");
			saveQuestionProps("text", "<p>Click SUBMIT in order to SAVE the information you entered. Don't worry, you WILL be able to CHANGE this form until " + formDeadlineText + ". Once you click Submit, you will receive an automatic email with instructions on how to EDIT the form.</p>", form, formID, 394L);


			updateOutput(textArea, "BIG/MEDIUM for " + className.toUpperCase() + ":");
			formID = formIDs[(1 + 2 * i)];
			updateOutput(textArea, "I. General information...");
			title = "Theater Form for Concert on " + dateToFullWords(concert) + " - Big/Medium Role";
			setProperty(form, formID, "pagetitle", title);
			setProperty(form, formID, "status", "DISABLED");
			setProperty(form, formID, "status", "ENABLED");
			setProperty(form, formID, "expireDate", formDeadlineProperty);
			saveQuestionProps("text", title, form, formID, 1L);
			saveQuestionProps("subHeader", className, form, formID, 1L);
			saveQuestionProps("text", title, form, formID, 380L);
			saveQuestionProps("subHeader", className, form, formID, 380L);

			introText = "<p class=\"p1\"><span style=\"font-size: 10pt;\"><span class=\"s1\" style=\"color: #ff0000;\">*</span><span class=\"s2\"> indicates a required question. You will not be able to submit the form without answering the question.</span></span></p><p class=\"p1\"><span class=\"s2\" style=\"font-size: 10pt;\"><strong>For help</strong>: Move your mouse&nbsp;over a question and read the information in the popup.</span></p><p class=\"p1\"><span class=\"s2\" style=\"font-size: 10pt;\">Please note: Every actor will be busy the whole day on <strong>Saturday, " + dateToFullWords(dressRehearsal) + "</strong> and on <strong>Sunday, " + dateToFullWords(concert) + "</strong>.</span></p>";
			saveQuestionProps("text", introText, form, formID, 11L);

			saveQuestionProps("text", "<p>In addition, every actor will have a dress rehearsal on stage on Saturday, " + dateToFullWords(dressRehearsal) + ".</p>", form, formID, 397L);

			saveQuestionProps("text", "<p><span style=\"font-size:12pt;\">We will try to satisfy your request but cannot promise.</span></p>\n<p><span style=\"font-size:12pt;\">Keep in mind that you <strong>will be able to make changes</strong> in this form after submission until " + formDeadlineText + ". When you click Submit at the very end, you will get an automatic email with instructions on how to edit your submitted form.</span></p>", form, formID, 10L);

			updateOutput(textArea, "II. Rehearsals with Misha...");
			saveQuestionProps("subHeader", mishaDateRange, form, formID, 7L);
			saveQuestionProps("subHeader", mishaDateRange, form, formID, 355L);

			mishaText = "<p><span style=\"font-size: 12pt;\">Misha's rehearsals usually take approximately&nbsp;<strong>2 hours for younger actors</strong>&nbsp;and&nbsp;<strong>2 to 2.5 hours for older actors</strong>&nbsp;(and longer in very rare cases).</span></p>\n<p><span style=\"font-size: 12pt;\">For Misha's rehearsals, you can select up to&nbsp;<strong>2 weekly conflicts</strong>&nbsp;(1 impossible, 1 undesirable)&nbsp;<span style=\"color: #0000ff;\">" + mishaDateRangeFormatted + "</span>.</span></p>\n<p><span style=\"font-size: 12pt;\">If you have ONE&nbsp;<strong>day/time</strong>&nbsp;out of the list below that is absolutely IMPOSSIBLE for rehearsals with Misha, you can log it here.</span></p>";
			saveQuestionProps("text", mishaText, form, formID, 12L);

			saveQuestionProps("options", mishaSlots, form, formID, 13L);
			saveQuestionProps("options", mishaSlots, form, formID, 14L);
			saveQuestionProps("options", mishaSlots, form, formID, 15L);

			saveQuestionProps("text", "<p><span style=\"font-size:12pt;\">You cannot choose more than <strong>1 IMPOSSIBLE weekly conflict</strong>, but now you can choose <strong>1 UNDESIRABLE weekly conflict</strong> <span style=\"color:#0000ff;\">" + mishaDateRangeFormatted + "</span>.</span></p>\n<p><span style=\"font-size:12pt;\">If you have a <strong>day/time on which you prefer not to rehearse with Misha</strong>, you can enter it below. We will try not to schedule it, but you may still have 1-2 rehearsals on that day/time (maybe more for a <strong>big</strong> role).</span></p>", form, formID, 18L);
			saveQuestionProps("text", "<p><span style=\"font-size:12pt;\">You have selected no IMPOSSIBLE weekly conflicts, so you can select <strong>up to two</strong> UNDESIRABLE weekly conflicts <span style=\"color:#0000ff;\">" + mishaDateRangeFormatted + "</span>.</span></p>\n<p><span style=\"font-size:12pt;\">If you have <strong>one or two day/time slots on which you prefer not to rehearse with Misha</strong>, you can enter them below. We will try not to schedule it, but you may still have 1-2 rehearsals on that day/time (maybe more for a <strong>big</strong> role).</span></p>", form, formID, 19L);

			updateOutput(textArea, "III. Working on lines with Nina...");
			saveQuestionProps("subHeader", ninaDateRange, form, formID, 17L);

			for (int slotNum = 0; slotNum < ninaQsBM.length; slotNum++)
				if (ninaQsBM[slotNum] != 0L)
				{

					saveQuestionProps("text", "", form, formID, ninaQsBM[slotNum]);
					saveQuestionProps("hidden", "Yes", form, formID, ninaQsBM[slotNum]);
				}
			for (int slotNum : ninaSlotChoices[i])
				if (ninaQsBM[slotNum] != 0L)
				{

					if (slotNum == ninaClassSlots[i]) {
						saveQuestionProps("options", nonOmitSlots[i], form, formID, ninaQsBM[slotNum]);
					}
					saveQuestionProps("text", ninaSlotsArray[slotNum], form, formID, ninaQsBM[slotNum]);
					saveQuestionProps("hidden", "No", form, formID, ninaQsBM[slotNum]);
				}
			saveQuestionProps("defaultValue", toHideList, form, formID, 415L);
			saveQuestionProps("options", ninaSlots, form, formID, 61L);
			saveQuestionProps("options", ninaSlots, form, formID, 63L);
			saveQuestionProps("options", ninaSlots, form, formID, 65L);

			saveQuestionProps("text", "<p><span style=\"font-size:10pt;\">Nina works on lines <strong>TWICE</strong>: <strong>first a reading</strong> and then the <strong>test on lines</strong> (may be more for a very big role).</span></p>\n<p><span style=\"font-size:10pt;color:#ff0000;\"><strong>Please notice: Misha's and Nina's schedules are completely separate. Even if you have logged a weekly conflict in Misha's section II, it does not carry over to Nina's section III and vice versa.</strong></span></p>\n<p><span style=\"font-size:10pt;color:#ff0000;\">For example, if Monday evening is your conflict for Nina's readings, you must log it below even if you logged Monday evening as a conflict for Misha's rehearsals.</span></p>\n<p><span style=\"font-size:10pt;\">Select weekly conflicts <span style=\"color:#0000ff;\">" + ninaDateRangeFormatted + "</span>. Choose up to <strong>one day/time slot</strong> that is IMPOSSIBLE for working with Nina. You can choose <strong>a second day/time slot</strong> that is UNDESIRABLE for working with Nina.</span></p>", form, formID, 20L);
			saveQuestionProps("text", "<p><span style=\"font-size:10pt;\">You cannot choose more than <strong>1 IMPOSSIBLE weekly conflict</strong>, but now you can choose <strong>1 UNDESIRABLE weekly conflict</strong> <span style=\"color:#0000ff;\">" + ninaDateRangeFormatted + "</span><strong>.</strong></span></p>\n<p><span style=\"color:#0000ff;\"><strong>If you change your IMPOSSIBLE weekly conflict, your UNDESIRABLE weekly conflict(s) will be erased, so make sure to re-select your UNDESIRABLE weekly conflict.</strong></span></p>", form, formID, 62L);
			saveQuestionProps("text", "<p><span style=\"font-size:10pt;\">You have selected no IMPOSSIBLE weekly conflicts, so you can select <strong>up to two</strong> UNDESIRABLE weekly conflicts <span style=\"color:#0000ff;\">" + ninaDateRangeFormatted + "</span>.</span></p>\n<p><span style=\"color:#0000ff;\"><strong>If you change your IMPOSSIBLE weekly conflict, your UNDESIRABLE weekly conflict(s) will be erased, so make sure to re-select your UNDESIRABLE weekly conflict.</strong></span></p>", form, formID, 64L);

			updateOutput(textArea, "IV. Vacation...");
			saveQuestionProps("text", "<p><span style=\"font-size:12pt;\">You <strong>do not</strong> have to rehearse on vacation, but if your stage group is in the area during vacation we could schedule some rehearsals.</span></p>\n<p><span style=\"font-size:12pt;\">If you leave for vacation a day earlier or return a day later, please, <strong>log these days as SPECIAL EXCEPTIONS in section V</strong>.</span></p>\n<p><span style=\"font-size:12pt;\">If you don't know your whole vacation plans yet, please enter what you already know below. You can submit the form without complete information today, but you must finalize this section by <strong>" + formDeadlineText + "</strong>.</span></p>", form, formID, 27L);

			saveQuestionProps("options", vacaOptions, form, formID, 50L);

			updateOutput(textArea, "V. Special exceptions...");
			saveQuestionProps("subHeader", fullRange, form, formID, 30L);
			saveQuestionProps("subHeader", fullRange, form, formID, 240L);

			SEintro = "<p><strong>If you have an important event on the day/time of the week which you claimed above as available (or undesirable) for work with NINA (<span style=\"color:#0000ff;\">" + ninaDateRange + "</span>) or with MISHA (<span style=\"color:#0000ff;\">" + mishaDateRange + "</span>), you can log this date as a Special Exception (SE).</strong></p>\n<p> </p>\n<p>WARNING: Be attentive so that you <span style=\"color:#ff0000;\">do not waste</span> your Special Exceptions (SE) on <span style=\"color:#ff0000;\">the day/time</span> when your rehearsals and readings <span style=\"color:#ff0000;\">cannot be scheduled</span>.</p>\n<p>1) If you selected <span style=\"color:#ff0000;\">Sunday morning</span> as your <span style=\"color:#ff0000;\">weekly IMPOSSIBLE</span> conflict for <span style=\"color:#ff0000;\">BOTH Nina’s readings</span> (Section III) <span style=\"color:#ff0000;\">and Misha’s rehearsals</span> (Section II), you <span style=\"color:#ff0000;\">do NOT have to log a slot</span> that falls on <span style=\"color:#ff0000;\">Sunday from 10 am to 1:30 pm</span> as your Special Exception.</p>\n<p>2) The rest is more complicated, and we cannot offer a general recipe. You have to make a specific decision about logging a Special Exception for each such case. You may need to see Nina's and Misha's schedule choices in Sections II and III. You will also have to remember that: <strong><span style=\"color:#0000ff;\">Nina reads from " + ninaStartText + " to " + ninaEndText + ", and Misha rehearses from " + mishaStartText + " to " + mishaEndText + "</span></strong>.</p>";
			saveQuestionProps("text", SEintro, form, formID, 359L);

			saveQuestionProps("text", SEexamples, form, formID, 387L);

			String SE1intro = "<p><strong><span style=\"font-size:10pt;\">If you have an important event on the day/time of the week which you claimed above as available (or undesirable) f<strong>or work with NINA (<span style=\"color:#0000ff;\">" + ninaDateRange + "</span>) or with MISHA (<span style=\"color:#0000ff;\">" + mishaDateRange + "</span>)</strong>, you can log this date below.</span></strong></p>\n<p>REMINDER: Be attentive so that you <span style=\"color:#ff0000;\">do not waste</span> your Special Exceptions (SE) on <span style=\"color:#ff0000;\">the day/time</span> when your rehearsals and readings <span style=\"color:#ff0000;\">cannot be scheduled</span>. See the explanation at the beginning of Section V.</p>";
			saveQuestionProps("text", SE1intro, form, formID, 251L);

			allSEintro = "<p><strong><span style=\"text-decoration:underline;\"><span style=\"font-size:10pt;\">IMPORTANT</span></span><span style=\"font-size:10pt;\"><span style=\"text-decoration:underline;\">:</span> Nina MUST finish all readings and some line tests before vacation. Therefore, if you enter <span style=\"color:#ff0000;\">MORE than ONE GUARANTEED</span> Special Exception <span style=\"color:#ff0000;\">for Nina's readings before vacation,</span> your <span style=\"color:#ff0000;\">UNDESIRABLE</span> weekly conflict <span style=\"color:#ff0000;\">for Nina's readings</span> (Section III) will be <span style=\"color:#ff0000;\">disregarded</span> and your <span style=\"color:#ff0000;\">IMPOSSIBLE</span> weekly conflict will be <span style=\"color:#ff0000;\">treated as UNDESIRABLE</span>.</span></strong></p>\n<p><strong><span style=\"font-size:10pt;\">If you have an important event on the day/time of the week which you claimed above as available (or undesirable) f<strong>or work with NINA (<span style=\"color:#0000ff;\">" + ninaDateRange + "</span>) or with MISHA (<span style=\"color:#0000ff;\">" + mishaDateRange + "</span>)</strong>, you can log this date below.</span></strong></p>\n<p><span style=\"font-size:10pt;\">REMINDER: Be attentive so that you </span><span style=\"color:#ff0000;\">do not waste</span><span style=\"font-size:10pt;\"> your Special Exceptions (SE) on </span><span style=\"color:#ff0000;\">the day/time</span><span style=\"font-size:10pt;\"> when your rehearsals and readings </span><span style=\"color:#ff0000;\">cannot be scheduled</span><span style=\"font-size:10pt;\">. <span style=\"color:#ff0000;\">See the explanation at the beginning of Section V.</span></span></p>";
			saveQuestionProps("text", allSEintro, form, formID, 32L);
			saveQuestionProps("text", allSEintro, form, formID, 252L);
			saveQuestionProps("text", allSEintro, form, formID, 253L);
			saveQuestionProps("text", allSEintro, form, formID, 254L);
			saveQuestionProps("text", allSEintro, form, formID, 255L);

			allUSEintro = "<p><strong>If you have an event on the day/time of the week which you claimed above as available (or undesirable) f<strong>or work with NINA (<span style=\"color:#0000ff;\">" + ninaDateRange + "</span>) or with MISHA (<span style=\"color:#0000ff;\">" + mishaDateRange + "</span>)</strong>, you can log this date below. This date will be scheduled only if absolutely necessary.</strong></p>\n<p><span style=\"font-size:10pt;\">REMINDER: Be attentive so that you </span><span style=\"color:#ff0000;\">do not waste</span><span style=\"font-size:10pt;\"> your Special Exceptions (SE) on </span><span style=\"color:#ff0000;\">the day/time</span><span style=\"font-size:10pt;\"> when your rehearsals and readings </span><span style=\"color:#ff0000;\">cannot be scheduled</span><span style=\"font-size:10pt;\">. <span style=\"color:#ff0000;\">See the explanation at the beginning of Section V.</span></span></p>";
			saveQuestionProps("text", allUSEintro, form, formID, 256L);
			saveQuestionProps("text", allUSEintro, form, formID, 257L);

			rangeWarning = "<p><strong><span style=\"font-size:10pt;color:#ff0000;\">You have selected a day outside of the available range. The date must be between " + ninaStartText + " and " + lastRehearsalText + ".<strong><span style=\"font-size:10pt;color:#ff0000;\"><strong><span style=\"font-size:10pt;color:#ff0000;\"> Please IGNORE the following prompt/question and clear the date.</span></strong></span></strong></span></strong></p>";
			saveQuestionProps("text", rangeWarning, form, formID, 265L);
			saveQuestionProps("text", rangeWarning, form, formID, 306L);
			saveQuestionProps("text", rangeWarning, form, formID, 270L);
			saveQuestionProps("text", rangeWarning, form, formID, 279L);
			saveQuestionProps("text", rangeWarning, form, formID, 317L);
			saveQuestionProps("text", rangeWarning, form, formID, 288L);
			saveQuestionProps("text", rangeWarning, form, formID, 326L);
			saveQuestionProps("text", rangeWarning, form, formID, 297L);

			mishaWarning = "<p><strong><span style=\"font-size:10pt;color:#ff0000;\">Misha will start rehearsing only on " + mishaStartText + ". You don't have to log this date/time as a SE. Please clear the date.</span></strong></p>";
			saveQuestionProps("text", mishaWarning, form, formID, 259L);
			saveQuestionProps("text", mishaWarning, form, formID, 308L);
			saveQuestionProps("text", mishaWarning, form, formID, 272L);
			saveQuestionProps("text", mishaWarning, form, formID, 319L);
			saveQuestionProps("text", mishaWarning, form, formID, 281L);
			saveQuestionProps("text", mishaWarning, form, formID, 328L);
			saveQuestionProps("text", mishaWarning, form, formID, 290L);
			saveQuestionProps("text", mishaWarning, form, formID, 299L);

			saveQuestionProps("defaultDate", DMSR, form, formID, 399L);
			saveQuestionProps("defaultDate", vacStart, form, formID, 402L);
			saveQuestionProps("defaultDate", vacEnd, form, formID, 403L);
			saveQuestionProps("defaultDate", firstDay, form, formID, 400L);
			saveQuestionProps("defaultDate", lastDay, form, formID, 401L);

			updateOutput(textArea, "VI. Parent volunteers...");
			saveQuestionProps("text", "<p>Click SUBMIT in order to SAVE the information you entered. Don't worry, you WILL be able to CHANGE this form until " + formDeadlineText + ". Once you click Submit, you will receive an automatic email with instructions on how to EDIT the form.</p>", form, formID, 383L);
		}

		updateOutput(textArea, "DONE!");

		long endTime = System.currentTimeMillis();
		String runTime = String.format("%d min, %d sec", new Object[] {
				Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(endTime - startTime)), 
				Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime - startTime))) });

		updateOutput(textArea, "Total time: " + runTime);

		System.exit(0);
	}

	public static void saveQuestionProps(String prop, String content, JotForm client, long formID, long qID) throws JSONException
	{
		HashMap<String, String> questionProperties = new HashMap();
		questionProperties.put(prop, content);
		JSONObject result = client.editFormQuestion(formID, qID, questionProperties);
	}

	public static void setProperty(JotForm client, long formID, String propertyKey, String newContent) throws JSONException {
		HashMap<String, String> propertiesMap = new HashMap();
		propertiesMap.put(propertyKey, newContent);
		JSONObject result = client.setFormProperties(formID, propertiesMap);
	}

	public static JotForm getForm() throws JSONException {
		JotForm client = new JotForm("9b8aa353e102a575e37733dcf14cd8b0");
		return client;
	}

	public static String generateVacation(String startDate, String endDate)
	{
		String month = dateToWords(startDate);
		month = startDate.substring(0, month.indexOf(' '));

		String beginning = startDate.substring(0, 3);
		int firstDay = Integer.parseInt(startDate.substring(3, 5));
		int lastDay = Integer.parseInt(endDate.substring(3, 5));
		int totalDays = lastDay - firstDay + 1;

		String end = startDate.substring(5);

		String[] dates = new String[totalDays];
		for (int i = 0; i < totalDays; i++) {
			dates[i] = (beginning + (firstDay + i) + end);
		}


		String[] days = new String[totalDays];
		for (int i = 0; i < totalDays; i++) {
			days[i] = dateToWords(dates[i]);
		}


		String result = days[0];
		for (int i = 1; i < totalDays; i++) {
			result = result + "|" + days[i];
		}


		return result;
	}

	public static String dateToFullWords(String date) {
		int month = Integer.parseInt(date.substring(0, date.indexOf('/')));
		date = date.substring(date.indexOf('/') + 1);
		int day = Integer.parseInt(date.substring(0, date.indexOf('/')));
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		return months[(month - 1)] + " " + day;
	}

	public static String dateToWords(String date) {
		int month = Integer.parseInt(date.substring(0, date.indexOf('/')));
		date = date.substring(date.indexOf('/') + 1);
		int day = Integer.parseInt(date.substring(0, date.indexOf('/')));
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };
		return months[(month - 1)] + " " + day;
	}

	public static String getDay(String date) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(date));
		int dayOfWeek = c.get(7);
		System.out.println(dayOfWeek);
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		return days[(dayOfWeek - 1)];
	}

	public static ArrayList<String> getGeneralInput()
			throws IOException
	{
		ArrayList<String> inputs = new ArrayList();

		setUIFont(new FontUIResource(new Font(new JLabel(" ").getFont().getFontName(), 0, 15)));

		JPanel panel = new JPanel(new java.awt.GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 0;
		c.fill = 1;
		c.anchor = 17;
		JDatePickerImpl DNSR;
		JDatePickerImpl DNFR;
		JDatePickerImpl DMSR;
		JDatePickerImpl concert;
		JComboBox mishaWorking;
		JComboBox mishaFriday;
		JComboBox dressRehearsal;
		JDatePickerImpl vacStart;
		JDatePickerImpl vacEnd; JDatePickerImpl deadline; JTextArea emails; try { List<String> lines = Files.readAllLines(Paths.get("DO-NOT-TOUCH/genParams.txt", new String[0]));

		DNSR = createDatePicker((String)lines.get(0));
		DNFR = createDatePicker((String)lines.get(1));
		DMSR = createDatePicker((String)lines.get(2));
		concert = createDatePicker((String)lines.get(3));
		String[] options = { "Yes", "No" };
		mishaWorking = new JComboBox(options);
		mishaWorking.setSelectedItem(lines.get(7));
		String[] optionsFriday = { "No", "Yes" };
		mishaFriday = new JComboBox(optionsFriday);
		mishaFriday.setSelectedItem(lines.get(9));
		String[] optionsDress = { "Sat", "Fri/Sat" };
		dressRehearsal = new JComboBox(optionsDress);
		dressRehearsal.setSelectedItem(lines.get(10));
		vacStart = createDatePicker((String)lines.get(4));
		vacEnd = createDatePicker((String)lines.get(5));
		deadline = createDatePicker((String)lines.get(6));
		emails = new JTextArea(((String)lines.get(8)).replaceAll(",", "\n"));
		} catch (Exception e) {
			DNSR = createDatePicker();
			DNFR = createDatePicker();
			DMSR = createDatePicker();
			concert = createDatePicker();
			String[] options = { "Yes", "No" };
			mishaWorking = new JComboBox(options);
			String[] optionsFriday = { "No", "Yes" };
			mishaFriday = new JComboBox(optionsFriday);
			String[] optionsDress = { "Sat", "Fri/Sat" };
			dressRehearsal = new JComboBox(optionsDress);
			vacStart = createDatePicker();
			vacEnd = createDatePicker();
			deadline = createDatePicker();
			emails = new JTextArea("russianstudio@comcast.net\nrussianstudio@gmail.com");
		}

		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 2;
		panel.add(new JLabel("Nina parameters:"), c);
		c.gridwidth = 1;
		c.gridy += 1;
		panel.add(new JLabel("DNSR:"), c);
		c.gridx = 1;
		panel.add(DNSR, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("DNFR:  "), c);
		c.gridx = 1;
		panel.add(DNFR, c);

		c.gridy += 1;
		c.gridx = 0;
		c.gridwidth = 2;
		panel.add(new JLabel("Misha parameters:"), c);
		c.gridwidth = 1;
		c.gridy += 1;
		panel.add(new JLabel("DMSR:"), c);
		c.gridx = 1;
		panel.add(DMSR, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Concert date:  "), c);
		c.gridx = 1;
		panel.add(concert, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Dress rehearsal days?  "), c);
		c.gridx = 1;
		panel.add(dressRehearsal, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Misha working?  "), c);
		c.gridx = 1;
		panel.add(mishaWorking, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Misha Fri 6:15/6:30?"), c);
		c.gridx = 1;
		panel.add(mishaFriday, c);

		c.gridy += 1;
		c.gridx = 0;
		c.gridwidth = 2;
		panel.add(new JLabel("Vacation parameters:"), c);
		c.gridwidth = 1;
		c.gridy += 1;
		panel.add(new JLabel("Vac start:"), c);
		c.gridx = 1;
		panel.add(vacStart, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Vac end:  "), c);
		c.gridx = 1;
		panel.add(vacEnd, c);

		c.gridy += 1;
		c.gridx = 0;
		c.gridwidth = 2;
		panel.add(new JLabel("Other parameters:"), c);
		c.gridwidth = 1;
		c.gridy += 1;
		panel.add(new JLabel("End of access:"), c);
		c.gridx = 1;
		panel.add(deadline, c);

		c.gridy += 1;
		c.gridx = 0;
		c.gridwidth = 2;
		panel.add(new JLabel("Emails (please list one per line):"), c);
		c.gridy += 1;
		emails.setLineWrap(true);
		emails.setRows(4);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(emails);
		scrollPane.setFocusable(true);
		panel.add(scrollPane, c);

		JFrame frame = new JFrame();
		frame.setResizable(false);
		int result = JOptionPane.showConfirmDialog(frame, panel, "General Parameters", 2);
		if (result != 0) {
			System.exit(0);
		}

		inputs.add(DNSR.getJFormattedTextField().getText());
		inputs.add(DNFR.getJFormattedTextField().getText());
		inputs.add(DMSR.getJFormattedTextField().getText());
		inputs.add(concert.getJFormattedTextField().getText());
		inputs.add(vacStart.getJFormattedTextField().getText());
		inputs.add(vacEnd.getJFormattedTextField().getText());
		inputs.add(deadline.getJFormattedTextField().getText());
		inputs.add(mishaWorking.getSelectedItem().toString());
		inputs.add(emails.getText().trim().replaceAll("\n", ","));
		inputs.add(mishaFriday.getSelectedItem().toString());
		inputs.add(dressRehearsal.getSelectedItem().toString());

		writeFile("DO-NOT-TOUCH/genParams.txt", inputs);

		return inputs;
	}

	public static JDatePickerImpl createDatePicker() {
		UtilDateModel model = new UtilDateModel();
		Calendar cal = Calendar.getInstance();
		model.setDate(cal.get(1), cal.get(2), cal.get(5));

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		return datePicker;
	}

	public static JDatePickerImpl createDatePicker(String strDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = dateFormat.parse(strDate);
		UtilDateModel model = new UtilDateModel();
		model.setValue(date);

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		return datePicker;
	}

	public static ArrayList<Object> getClassInput(int classNum) throws IOException {
		ArrayList<Object> inputs = new ArrayList();
		ArrayList<String> newLines = new ArrayList();

		JPanel panel = new JPanel(new java.awt.GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 0;
		c.fill = 1;
		c.anchor = 17;

		c.gridy = 0;
		c.gridx = 0;
		panel.add(new JLabel("Class name:"), c);
		c.gridy += 1;
		JTextField className = new JTextField(20);
		panel.add(className, c);

		c.gridy += 1;
		panel.add(new JLabel("Nina slots:"), c);
		String[] ninaSlotsArray = { "Mon from 4 or 4:30 pm for 60-90 min", "Mon from 5:30 or 6 pm for 60-90 min", "Tue from 4 or 4:30 pm for 60-90 min", "Tue from 5:30 or 6 pm for 60-90 min", "Wed from 3:30 or 4 pm for 60-90 min", "Wed from 4 or 4:30 pm for 60-90 min", "Wed from 5:30 or 6 pm for 60-90 min", "Thu from 3:30 or 4 pm for 60-90 min", "Thu from 4 or 4:30 pm for 60-90 min", "Thu from 5:30 or 6 pm for 60-90 min", "Fri from 3:30 or 4 pm for 60-90 min", "Fri from 4 or 4:30 pm for 60-90 min", "Fri from 5:30 or 6 pm for 60-90 min", "Sat from 10 am for 60-90 min", "Sat from 11:30 am or 12 pm for 60-90 min", "Sun from 10 am for 60-90 min" };
		ArrayList<JCheckBox> checkBoxes = new ArrayList();
		for (int slot = 0; slot < ninaSlotsArray.length; slot++) {
			c.gridy += 1;
			JCheckBox box = new JCheckBox(ninaSlotsArray[slot]);
			checkBoxes.add(box);
			panel.add(box, c);
		}

		c.gridy += 1;
		panel.add(new JLabel("Class #" + classNum + " slot:"), c);
		c.gridy += 1;
		JComboBox classSlot = new JComboBox(ninaSlotsArray);
		panel.add(classSlot, c);

		c.gridy += 1;
		panel.add(new JLabel("Omit choice (choose one to OMIT):"), c);
		c.gridy += 1;
		final String[] type1Options = { "Both 3:30 and 4 pm are OK", "3:30 pm only (I have to leave at 5)", "4 pm only (I cannot arrive earlier)" };
		final String[] type2Options = { "Both 4 and 4:30 pm are OK", "4 pm only (I have to leave at 5:30)", "4:30 pm only (I cannot arrive earlier)" };
		final String[] type3Options = { "Both 5:30 and 6 pm are OK", "5:30 pm only (I have to leave at 7)", "6 pm only (I cannot arrive earlier)" };
		String[] typeOptions = type1Options;
		final JComboBox omitSlot = new JComboBox(type1Options);
		panel.add(omitSlot, c);

		final DefaultComboBoxModel model = (DefaultComboBoxModel)omitSlot.getModel();
		classSlot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (classSlot.getSelectedItem().toString().contains("3:30 or 4 pm")) {
					model.removeAllElements();
					for (String opt : type1Options) {
						model.addElement(opt);
					}
					omitSlot.setModel(model);
				}
				else if (classSlot.getSelectedItem().toString().contains("4 or 4:30 pm")) {
					model.removeAllElements();
					for (String opt : type2Options) {
						model.addElement(opt);
					}
					omitSlot.setModel(model);
				}
				else if (classSlot.getSelectedItem().toString().contains("5:30 or 6 pm")) {
					model.removeAllElements();
					for (String opt : type3Options) {
						model.addElement(opt);
					}
					omitSlot.setModel(model);
				}
			}
		});
		try
		{
			List<String> lines = Files.readAllLines(Paths.get("DO-NOT-TOUCH/class" + classNum + ".txt", new String[0]));

			className.setText((String)lines.get(0));
			String savedSlots = (String)lines.get(1);
			for (int i = 0; i < checkBoxes.size(); i++) {
				if (savedSlots.contains("|" + i + "|")) {
					((JCheckBox)checkBoxes.get(i)).setSelected(true);
				}
			}
			classSlot.setSelectedIndex(Integer.parseInt((String)lines.get(2)));
			omitSlot.setSelectedIndex(Integer.parseInt((String)lines.get(3)));
		}
		catch (Exception localException) {}


		JFrame frame = new JFrame();
		frame.setResizable(false);
		int result = JOptionPane.showConfirmDialog(frame, panel, "Class #" + classNum + " Parameters", 2);
		if (result != 0) {
			System.exit(0);
		}

		inputs.add(className.getText());
		newLines.add(className.getText());
		int numSlotsChosen = 0;
		for (JCheckBox box : checkBoxes) {
			if (box.isSelected()) {
				numSlotsChosen++;
			}
		}
		int[] slotsChosen = new int[numSlotsChosen];
		int currIndex = 0;
		for (int i = 0; i < checkBoxes.size(); i++) {
			if (((JCheckBox)checkBoxes.get(i)).isSelected()) {
				slotsChosen[currIndex] = i;
				currIndex++;
			}
		}
		inputs.add(slotsChosen);
		newLines.add(arrayToString(slotsChosen));
		inputs.add(Integer.valueOf(classSlot.getSelectedIndex()));
		newLines.add("" + classSlot.getSelectedIndex());
		String nonOmitSlots = "";
		for (int i = 0; i < model.getSize(); i++) {
			if (i != omitSlot.getSelectedIndex()) {
				nonOmitSlots = nonOmitSlots + "|" + model.getElementAt(i);
			}
		}
		inputs.add(nonOmitSlots.substring(1));
		newLines.add("" + omitSlot.getSelectedIndex());

		writeFile("DO-NOT-TOUCH/class" + classNum + ".txt", newLines);

		return inputs;
	}

	public static JTextArea displayOutput(String initial)
	{
		JTextArea textArea = new JTextArea();
		textArea.setText(initial);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		textArea.setFont(new Font(textArea.getFont().getFontName(), 0, 15));

		JFrame frame = new JFrame();
		frame.setSize(300, 600);
		frame.setResizable(false);
		frame.add(scrollPane);
		frame.setVisible(true);

		return textArea;
	}

	public static void updateOutput(JTextArea textArea, String str) {
		textArea.setText(textArea.getText() + "\n" + str);
	}

	public static void showInputError() {
		JOptionPane.showMessageDialog(null, "ERROR. Please try re-entering your parameters.", "Input Error", 0);
		System.exit(0);
	}

	public static void showOtherError() {
		JOptionPane.showMessageDialog(null, "ERROR. An unknown issue has occurred. Please try again later.", "Unknown Error", 0);
		System.exit(0);
	}

	public static void setUIFont(FontUIResource f) {
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if ((value instanceof FontUIResource)) {
				FontUIResource orig = (FontUIResource)value;
				Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
				UIManager.put(key, new FontUIResource(font));
			}
		}
	}

	public static void getQuestion(JotForm client, long formID, int qID) throws JSONException
	{
		JSONObject questionsFull = client.getFormQuestions(formID);
		JSONObject questions = (JSONObject)questionsFull.get("content");
		JSONObject question = (JSONObject)questions.get("" + qID);
		System.out.println(question);
	}

	public static void getQuestion(JotForm client, long formID, String str) throws JSONException {
		JSONObject questionsFull = client.getFormQuestions(formID);
		String questionsFullString = questionsFull.toString();
		int i = questionsFullString.indexOf(str);
		if (i < 1000) {
			System.out.println(questionsFullString.substring(0, i + 1000));
		}
		else {
			System.out.println(questionsFullString.substring(i - 1000, i + 2000));
		}
	}

	public static void getProperty(JotForm client, long formID, String propertyKey) throws JSONException {
		JSONObject propertiesFull = client.getFormProperties(formID);
		JSONObject properties = (JSONObject)propertiesFull.get("content");
		System.out.println(properties.get(propertyKey));
	}

	public static void propertySearch(JotForm client, long formID, String str) throws JSONException {
		JSONObject propertiesFull = client.getFormProperties(formID);
		String propertiesFullString = propertiesFull.toString();
		int i = propertiesFullString.indexOf(str);
		System.out.println(propertiesFullString);
		//	  if (i < 1000) {
		//		  System.out.println(propertiesFullString.substring(0, i + 1000));
		//	  }
		//	  else {
		//		  System.out.println(propertiesFullString.substring(i - 1000, propertiesFullString.length()));
		//	  }
	}

	public static void printArray(int[] arr) {
		String output = "";
		int[] arrayOfInt = arr;int j = arr.length; for (int i = 0; i < j; i++) { int i1 = arrayOfInt[i];
		output = output + i1 + ", ";
		}
		System.out.println(output);
	}

	public static boolean exists(String filename)
	{
		File f = new File(filename);
		return f.exists();
	}

	public static void writeFile(String filename, ArrayList<String> lines) throws IOException {
		java.nio.file.Path file = Paths.get(filename, new String[0]);
		Files.write(file, lines, java.nio.charset.Charset.forName("UTF-8"), new java.nio.file.OpenOption[0]);
	}

	public static String arrayToString(int[] arr) {
		String str = "|";
		int[] arrayOfInt = arr;int j = arr.length; for (int i = 0; i < j; i++) { int i1 = arrayOfInt[i];
		str = str + i1 + "|";
		}
		return str;
	}
}
