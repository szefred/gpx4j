package org.casaca.gpx4j.core.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.casaca.gpx4j.core.driver.reader.IGpxReader;
import org.casaca.gpx4j.core.driver.writer.IGpxWriter;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxReaderException;
import org.casaca.gpx4j.core.exception.GpxWriterException;
import org.casaca.gpx4j.core.util.Constants;

public class GpxDriver {
	private IGpxReader reader;
	private IGpxWriter writer;
	private Properties properties;
	
	private static GpxDriver driver = null;
	
	public static GpxDriver getGpxDriver(){
		if(driver == null)
			driver = new GpxDriver();
		
		return driver;
	}
	
	private GpxDriver(){
		this.properties = null;
		this.reader = null;
		this.writer = null;
	}
	
	public void loadProperties(String filePath) throws GpxFileNotFoundException, GpxIOException{
		this.properties = new Properties();
		try {
			this.properties.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			throw new GpxFileNotFoundException("The properties file \""+filePath+"\"");
		} catch (IOException e) {
			throw new GpxIOException(e);
		}
	}
	
	public void loadDefaultProperties() throws GpxFileNotFoundException, GpxIOException {
		this.properties = new Properties();
		try {
			this.properties.load(Constants.class.getResourceAsStream(Constants.APPLICATION_DEFAULT_PROPERTIES_FILENAME));
		} catch (FileNotFoundException e) {
			throw new GpxFileNotFoundException("Error loading default properties");
		} catch (IOException e) {
			throw new GpxIOException(e);
		}
	}
	
	public Properties getProperties(){
		return this.properties;
	}
	
	public IGpxReader getReader() throws GpxReaderException, GpxPropertiesException{
		if(this.reader == null){
			if(this.properties == null)
				throw new GpxPropertiesException("Properties not loaded. Please load properties before use the reader");
			
			String className = this.properties.getProperty(Constants.DRIVER_READER_CLASS_NAME);
			if(className == null)
				throw new GpxReaderException("Property "+Constants.DRIVER_READER_CLASS_NAME+" not found in properties file");
			
			try {
				this.reader = (IGpxReader)Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				throw new GpxReaderException(e);
			} catch (IllegalAccessException e) {
				throw new GpxReaderException(e);
			} catch (ClassNotFoundException e) {
				throw new GpxReaderException(e);
			}
		}
		
		return this.reader;
	}
	
	public IGpxReader createReader() throws GpxReaderException, GpxPropertiesException{
		IGpxReader reader = null;
		if(this.properties == null)
			throw new GpxPropertiesException("Properties not loaded. Please load properties before use the reader");

		String className = this.properties.getProperty(Constants.DRIVER_READER_CLASS_NAME);
		if(className == null)
			throw new GpxReaderException("Property "+Constants.DRIVER_READER_CLASS_NAME+" not found in properties file");

		try {
			reader = (IGpxReader)Class.forName(className).newInstance();
			return reader;
		} catch (InstantiationException e) {
			throw new GpxReaderException(e);
		} catch (IllegalAccessException e) {
			throw new GpxReaderException(e);
		} catch (ClassNotFoundException e) {
			throw new GpxReaderException(e);
		}
	}
	
	public IGpxWriter getWriter() throws GpxWriterException, GpxPropertiesException {
		if(this.writer == null){
			if(this.properties == null)
				throw new GpxPropertiesException("Properties not loaded. Please load properties before use the writer");
			
			String className = this.properties.getProperty(Constants.DRIVER_WRITER_CLASS_NAME);
			if(className == null)
				throw new GpxWriterException("Property "+Constants.DRIVER_WRITER_CLASS_NAME+" not found in properties file");
			
			try {
				this.writer = (IGpxWriter)Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				throw new GpxWriterException(e);
			} catch (IllegalAccessException e) {
				throw new GpxWriterException(e);
			} catch (ClassNotFoundException e) {
				throw new GpxWriterException(e);
			}
		}
		
		return this.writer;
	}
	
	public IGpxWriter createWriter() throws GpxWriterException, GpxPropertiesException {
		IGpxWriter writer = null;
			if(this.properties == null)
				throw new GpxPropertiesException("Properties not loaded. Please load properties before use the writer");
			
			String className = this.properties.getProperty(Constants.DRIVER_WRITER_CLASS_NAME);
			if(className == null)
				throw new GpxWriterException("Property "+Constants.DRIVER_WRITER_CLASS_NAME+" not found in properties file");
			
			try {
				writer = (IGpxWriter)Class.forName(className).newInstance();
				return writer;
			} catch (InstantiationException e) {
				throw new GpxWriterException(e);
			} catch (IllegalAccessException e) {
				throw new GpxWriterException(e);
			} catch (ClassNotFoundException e) {
				throw new GpxWriterException(e);
			}
	}
}