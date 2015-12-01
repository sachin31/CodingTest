package com.example.weatherapp.ui.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

/**
 * This class acts as a reusable component to create different types of dialogs
 *
 */
public class DialogCreator {
	/** The context for the calling activity **/
	private final Context context;

	private String positiveButtonText = "Yes";

	private String negativeButtonText = "No";

	private String neutralButtonText = "Cancel";

	/** constant for the DIALOG_ALERT_INVALID **/
	public static final int DIALOG_OK = 0;
	/** constant for the DIALOG_CONFIRM **/
	public static final int DIALOG_CONFIRM = 1;
	/** constant for the PROGRESS_DIALOG_HOR **/
	public static final int PROGRESS_DIALOG_HOR = 2;
	/** constant for the DIALOG_THREE_BUTTONS **/
	public static final int DIALOG_THREE_BUTTONS = 3;
	/** constant for the PROGRESS_DIALOG_VER **/
	public static final int PROGRESS_DIALOG_CIRCULAR = 4;

	/**
	 * Constructor for the class DialogCreator
	 * 
	 * @param context
	 *            The context for the calling activity
	 */
	public DialogCreator(Context context) {
		this.context = context;
	}

	/**
	 * Align alert dialog text centrally
	 * 
	 * @param alertDialog
	 *            AlertDialog
	 */
	public void centerAlignDialogText(AlertDialog alertDialog) {
		final TextView messageView = (TextView) alertDialog.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);
	}

	/**
	 * function to create dialog
	 * 
	 * @param id
	 *            the type of dialog
	 * @param yes
	 *            the yes OnClickListener if required else null
	 * @param no
	 *            the no OnClickListener if required else null
	 * @param message
	 *            the message to be displayed in the dialog
	 * @return the corresponding dialog
	 */

	public AlertDialog createDialog(int id, DialogInterface.OnClickListener yes, DialogInterface.OnClickListener no, String title, String message) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		ProgressDialog dialog;
		OnClickListener neutral;
		switch (id) {

		case DialogCreator.DIALOG_CONFIRM:
			builder.setTitle("Alert");
			if (title != null) {
				builder.setTitle(title);
			}
			builder.setMessage(message);
			builder.setCancelable(false);

			if (yes == null) {

				yes = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						((Activity) context).removeDialog(DIALOG_CONFIRM);
					}
				};
			}
			if (no == null) {

				no = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						((Activity) context).removeDialog(DIALOG_CONFIRM);
					}
				};
			}
			builder.setPositiveButton(positiveButtonText, yes);
			builder.setNegativeButton(negativeButtonText, no);
			break;

		case DialogCreator.DIALOG_OK:
			builder.setTitle("Alert");
			if (title != null) {
				builder.setTitle(title);
			}
			builder.setMessage(message);
			builder.setCancelable(false);
			if (yes == null) {

				yes = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						((Activity) context).removeDialog(DIALOG_OK);
					}
				};
			}
			builder.setPositiveButton("Ok", yes);
			break;

		case DialogCreator.DIALOG_THREE_BUTTONS:
			builder.setTitle("Alert");
			if (title != null) {
				builder.setTitle(title);
			}
			builder.setMessage(message);
			builder.setCancelable(false);

			if (yes == null) {

				yes = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						((Activity) context).removeDialog(DIALOG_THREE_BUTTONS);
					}
				};
			}
			if (no == null) {

				no = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						((Activity) context).removeDialog(DIALOG_THREE_BUTTONS);
					}
				};
			}

			neutral = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					((Activity) context).removeDialog(DIALOG_THREE_BUTTONS);
				}
			};

			builder.setPositiveButton(positiveButtonText, yes);
			builder.setNegativeButton(neutralButtonText, neutral);
			builder.setNeutralButton(negativeButtonText, no);
			break;

		case DialogCreator.PROGRESS_DIALOG_HOR:
			if (title != null) {
				builder.setTitle(title);
			}
			dialog = new ProgressDialog(context);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMessage(message);
			dialog.setIndeterminate(false);
			// dialog.setCancelable(false);
			dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_SEARCH) {
						return true;
					} else
						return false;
				}
			});
			return dialog;
		case DialogCreator.PROGRESS_DIALOG_CIRCULAR:
			if (title != null) {
				builder.setTitle(title);
			}
			dialog = new ProgressDialog(context);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage(message);
			dialog.setIndeterminate(false);
			// dialog.setCancelable(false);
			dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_SEARCH) {
						return true;
					} else
						return false;
				}
			});
			return dialog;

		default:
			break;
		}
		return builder.create();
	}

	public void initialize() {
		positiveButtonText = "Yes";
		negativeButtonText = "No";
	}

	/**
	 * If you use this parameter. Do make sure you reset the value once you are
	 * done with the dialog using initialize()
	 * 
	 * @param negativeButtonText
	 *            the negativeButtonText to set
	 */
	public void setNegativeButtonText(String negativeButtonText) {
		this.negativeButtonText = negativeButtonText;
	}

	public void setNeutralButtonText(String neutralButtonText) {
		this.neutralButtonText = neutralButtonText;
	}

	/**
	 * If you use this parameter. Do make sure you reset the value once you are
	 * done with the dialog using initialize()
	 * 
	 * @param positiveButtonText
	 *            the positiveButtonText to set
	 */
	public void setPositiveButtonText(String positiveButtonText) {
		this.positiveButtonText = positiveButtonText;
	}

	/**
	 * Display Alert Dialog
	 * 
	 * @param dialogType
	 *            dialog type
	 * @param yes
	 *            positive button listener
	 * @param no
	 *            negative button listener
	 * @param title
	 *            alert dialog title
	 * @param message
	 *            message
	 * @param centerAlignText
	 *            true if text needs to align centrally, false otherwise
	 * @return alert dialog
	 */
	public AlertDialog showDialog(final int dialogType, OnClickListener yes, OnClickListener no, String title, String message, final boolean centerAlignText) {
		final AlertDialog alertDialog = createDialog(dialogType, yes, no, title, message);
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				try {
					alertDialog.show();
					if (centerAlignText && (dialogType != PROGRESS_DIALOG_HOR)) {
						centerAlignDialogText(alertDialog);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return alertDialog;
	}

	/**
	 * Display alert dialog
	 * 
	 * @param dialogType
	 *            dialog type
	 * @param title
	 *            Alert dialog title
	 * @param message
	 *            message
	 */
	public AlertDialog showDialog(int dialogType, String title, String message) {
		AlertDialog alertDialog = showDialog(dialogType, null, null, title, message, true);
		return alertDialog;
	}
}
