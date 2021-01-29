package com.vobi.bank.utility;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */
public class Utilities {

	public static boolean isNumeric(String word) {
		boolean ret = false;
		Pattern pat = Pattern.compile("[^0-9',.\\s]");
		Matcher mat = pat.matcher(word);
		if (!mat.find()) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 
	 * @param word
	 * @return Expresion regular "(\\d){1,10}\\.(\\d){1,10}" (\\d)digito {1,10}de 1
	 *         a 10 caracteres \\. punto
	 * 
	 */
	public static boolean isDecimal(String word) {
		boolean ret = false;
		Pattern pat = Pattern.compile("(\\d){1,8}\\.(\\d){0,2}");
		Matcher mat = pat.matcher(word);
		if (!mat.find()) {
			ret = true;
		}
		return ret;
		// DoubleValidator doubleValidator = new DoubleValidator();
		// return doubleValidator.isValid(word);
	}

	public static boolean checkNumberAndCheckWithPrecisionAndScale(String fieldValue, Integer precision,
			Integer scale) {
		boolean ret = false;
		if (fieldValue != null && precision != null && scale != null) {
			if (fieldValue.contains("E") && scale != 0) {
				String dfString = "# # . # # # # # # # # # # # # # # # #";
				dfString = dfString.replace(" ", "");
				DecimalFormat df = new DecimalFormat(dfString);
				fieldValue = df.format(new Double(fieldValue));
				if (fieldValue.length() > 0 && !fieldValue.contains(".")) {
					fieldValue = fieldValue + ".0";
				}
			}
			fieldValue = fieldValue.replace(".", "%");
			String[] spitedFieldValue = fieldValue.split("%");
			if (spitedFieldValue.length == 2 && precision != 0) {
				String precisionTmp = spitedFieldValue[0];
				String scaleTmp = spitedFieldValue[1];
				if (!isNumeric(precisionTmp)) {
					return false;
				}
				if (!isNumeric(scaleTmp)) {
					return false;
				}
				if ((precisionTmp.length() <= precision) && (scaleTmp.length() <= scale)) {
					ret = true;
				}
			} else {
				if (spitedFieldValue.length == 1 && precision != 0 && scale == 0) {
					String precisionTmp = spitedFieldValue[0];
					if (!isNumeric(precisionTmp)) {
						return false;
					}
					if ((precisionTmp.length() <= precision)) {
						ret = true;
					}
				} else {
					return false;
				}
			}
		}
		return ret;
	}

	public static boolean checkWordAndCheckWithlength(String word, Integer length) {
		boolean ret = false;
		if (word.length() <= length) {
			ret = true;
		}
		return ret;
	}

	public static boolean isOnlyLetters(String word) {
		boolean ret = false;
		Pattern pat = Pattern.compile("[^A-Za-z0-9',.\\s]");
		Matcher mat = pat.matcher(word);
		if (!mat.find()) {
			ret = true;
		}
		return ret;
	}

	public static String formatDateWithoutTimeInAStringForBetweenWhere(Date fecha) {
		int year = fecha.getYear() + 1900;
		int month = fecha.getMonth() + 1;
		int day = fecha.getDate();
		String date = "" + year + "-" + month + "-" + day;
		return date;
	}

	public static boolean validationsList(List list) {
		if (list != null) {
			if (!list.isEmpty() && list.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Método para valida emails
	 * 
	 * @author Camilo Andrés Cifuentes Grass
	 * @version 2018/01/17
	 * @param sEmail el email a verificar
	 * @return true si es un email válido, false en caso que no sea así.
	 */
	public static boolean isValidEmail(String sEmail) {
		boolean isValid = false;

		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pat = Pattern.compile(EMAIL_PATTERN);
		Matcher mat = pat.matcher(sEmail);

		if (pat.matcher(sEmail).matches()) {
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * 
	 * @param object
	 * @param object2
	 * @param privateFields
	 * @return
	 * @throws Exception
	 */
	public static boolean matchClasses(Object object, Object object2, boolean privateFields) throws Exception {

		boolean couldPerformTask = false;
		Object paramsObj[] = {};

		Class tmpClass = object.getClass();
		Class tmpClass2 = object2.getClass();

		Field field[] = tmpClass.getDeclaredFields();
		Field field2[] = tmpClass2.getDeclaredFields();

		Method method[] = tmpClass.getMethods();
		Method method2[] = tmpClass2.getMethods();

		String tmpName = new String();
		String tmpName2 = new String();

		Field tmpField;
		Field tmpField2;

		Method tmpMethod;
		Method tmpMethod2;

		Object tmpInfo = new Object();
		Object tmpInfo2 = new Object();

		Class[] paramTypes = null;
		Class[] paramTypes1 = null;

		if (privateFields) {

			try {
				if (method != null && method.length > 0) {

					for (int i = 0; i < method.length; i++) {
						tmpMethod = method[i];
						tmpMethod2 = method2[i];

						if (tmpMethod != null && tmpMethod2 != null) {
							paramTypes = tmpMethod.getParameterTypes();
							tmpName = tmpMethod.getName().substring(0, 3);

							paramTypes1 = tmpMethod2.getParameterTypes();
							tmpName2 = tmpMethod2.getName().substring(0, 3);

							if (tmpName.equals("get") && tmpName2.equals("get")) {

								tmpInfo = tmpMethod.invoke(object, paramsObj);
								tmpInfo2 = tmpMethod2.invoke(object2, paramsObj);

								if (tmpInfo != null && tmpInfo2 != null) {
									try {
										if (tmpInfo.equals(tmpInfo2)) {
											couldPerformTask = true;
										} else {
											return false;
										}
									} catch (Exception e) {

										if (tmpInfo == tmpInfo2) {
											couldPerformTask = true;
										} else {
											return false;
										}
									}
								}
							}
						}
					}

				} else {
					throw new Exception("One of the the Classes has no \"get\" methods please check");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			try {
				for (int j = 0; j < field.length; j++) {
					tmpField = field[j];
					tmpField2 = field2[j];

					if (tmpField != null && tmpField2 != null) {

						tmpInfo = tmpField.get(object);
						tmpInfo2 = tmpField2.get(object2);

						if (tmpInfo != null && tmpInfo2 != null) {
							try {
								if (tmpInfo.equals(tmpInfo2)) {
									couldPerformTask = true;
								} else {
									return false;
								}
							} catch (Exception e) {

								if (tmpInfo == tmpInfo2) {
									couldPerformTask = true;
								} else {
									return false;
								}
							}
						}
					} else {
						throw new Exception("One of the the Classes has no fields methods please check");
					}
				}
			} catch (IllegalAccessException ea) {
				throw new Exception(
						"One of the objects you are trying to compare has its fields private please use the method way");
			} catch (Exception e) {
				throw e;
			}
		}
		return couldPerformTask;
	}

	public String constructQuery(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates)
			throws Exception {
		String where = new String();
		String tempWhere = new String();

		if (variables != null) {
			for (int i = 0; i < variables.length; i++) {
				if ((variables[i] != null) && (variables[i + 1] != null) && (variables[i + 2] != null)
						&& (variables[i + 3] != null)) {
					String variable = (String) variables[i];
					Boolean booVariable = (Boolean) variables[i + 1];
					Object value = variables[i + 2];
					String comparator = (String) variables[i + 3];

					if (booVariable.booleanValue()) {
						tempWhere = (tempWhere.length() == 0)
								? ("(model." + variable + " " + comparator + " \'" + value + "\' )")
								: (tempWhere + " AND (model." + variable + " " + comparator + " \'" + value + "\' )");
					} else {
						tempWhere = (tempWhere.length() == 0)
								? ("(model." + variable + " " + comparator + " " + value + " )")
								: (tempWhere + " AND (model." + variable + " " + comparator + " " + value + " )");
					}
				}

				i = i + 3;
			}
		}

		if (variablesBetween != null) {
			for (int j = 0; j < variablesBetween.length; j++) {
				if ((variablesBetween[j] != null) && (variablesBetween[j + 1] != null)
						&& (variablesBetween[j + 2] != null) && (variablesBetween[j + 3] != null)
						&& (variablesBetween[j + 4] != null)) {
					String variable = (String) variablesBetween[j];
					Object value = variablesBetween[j + 1];
					Object value2 = variablesBetween[j + 2];
					String comparator1 = (String) variablesBetween[j + 3];
					String comparator2 = (String) variablesBetween[j + 4];
					tempWhere = (tempWhere.length() == 0)
							? ("(" + value + " " + comparator1 + " " + variable + " and " + variable + " " + comparator2
									+ " " + value2 + " )")
							: (tempWhere + " AND (" + value + " " + comparator1 + " " + variable + " and " + variable
									+ " " + comparator2 + " " + value2 + " )");
				}

				j = j + 4;
			}
		}

		if (variablesBetweenDates != null) {
			for (int k = 0; k < variablesBetweenDates.length; k++) {
				if ((variablesBetweenDates[k] != null) && (variablesBetweenDates[k + 1] != null)
						&& (variablesBetweenDates[k + 2] != null)) {
					String variable = (String) variablesBetweenDates[k];
					Object object1 = variablesBetweenDates[k + 1];
					Object object2 = variablesBetweenDates[k + 2];
					String value = null;
					String value2 = null;

					try {
						Date date1 = (Date) object1;
						Date date2 = (Date) object2;
						value = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date1);
						value2 = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date2);
					} catch (Exception e) {
						throw e;
					}

					tempWhere = (tempWhere.length() == 0)
							? ("(model." + variable + " between \'" + value + "\' and \'" + value2 + "\')")
							: (tempWhere + " AND (model." + variable + " between \'" + value + "\' and \'" + value2
									+ "\')");
				}

				k = k + 2;
			}
		}

		if (tempWhere.length() == 0) {
			where = "";
		} else {
			where = "where (" + tempWhere + ")";
		}

		return where;
	}

	/**
	 * @author Camilo Puente
	 * @author Frank Edward Daza González
	 * @date Nov 1, 2017
	 * @param lblName
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public static String errorComponentLogic(String lblName, Locale locale) throws Exception {
		InputStream is = null;
		is = Utilities.class.getResourceAsStream("/i18n/messages_" + locale.getLanguage() + ".properties");

		String exception = "";
		Properties p = new Properties();
		p.load(is);
		exception = p.getProperty(lblName);
		return exception;
	}

	/**
	 * @author Johan Steve Bejarano Fiesco
	 * @version 2018/06/05
	 * @param lblName
	 * @param locale
	 * @param params
	 * @return {@code String }
	 * @throws Exception
	 */
	public static String errorComponentLogic(String lblName, Locale locale, String... params) throws Exception {
		InputStream is = null;
		is = Utilities.class.getResourceAsStream("/i18n/messages_" + locale.getLanguage() + ".properties");

		String exception = "";
		Properties p = new Properties();
		p.load(is);
		exception = p.getProperty(lblName);

		if (exception != null && params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				exception = exception.replaceAll("\\$" + (i + 1), params[i]);
			}
		}

		return exception;
	}

	public static String generarToken() throws NoSuchAlgorithmException {
		String token = UUID.randomUUID().toString();
		return token;
	}

	/**
	 * Retorna una fecha inicial con horas, minutos y segundos: 00:00:00
	 * 
	 * @author Frank Edward Daza González
	 * @date Nov 20, 2017
	 * @param date
	 * @return Date
	 * @throws Exception
	 */
	public static Date buildStartDate(Date date) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Retorna una fecha final con horas, minutos y segundos: 23:59:59
	 * 
	 * @author Frank Edward Daza González
	 * @date Nov 20, 2017
	 * @param date
	 * @return Date
	 * @throws Exception
	 */
	public static Date buildFinalDate(Date date) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	/**
	 * Funcíón para redondear un número double a los decimales que le ingresen
	 * 
	 * @author Camilo Delgado
	 * @version Mar 23, 2018
	 * @since 1.8
	 * @param numero
	 * @param decimales
	 * @return
	 *
	 */
	public static double redondear(double numero, int decimales) {
		boolean negativo = false;
		if (numero < 0) {
			numero *= -1;
			negativo = true;
		}
		double redondeado = Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
		return negativo ? redondeado * -1 : redondeado;
	}

	/**
	 * Función para obtener el número que sea múltiplo de "multiploDe" y mayor que
	 * "mayorQue"
	 *
	 * @author Camilo Delgado
	 * @version Mar 24, 2018
	 * @since 1.8
	 * @param multiploDe
	 * @param mayorQue
	 * @return
	 *
	 */
	public static Integer siguienteMultiploMayorOIgual(Integer multiploDe, Integer mayorQue) {
		Double division = mayorQue.doubleValue() / multiploDe.doubleValue();
		division = (Math.ceil(division));
		Integer numeroSiguiente = division.intValue() * multiploDe;
		return numeroSiguiente;
	}

}