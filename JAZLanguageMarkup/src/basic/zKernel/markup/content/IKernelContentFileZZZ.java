package basic.zKernel.markup.content;

import java.io.File;

import basic.zKernel.html.reader.KernelReaderHtmlZZZ;

public interface IKernelContentFileZZZ {
	public File getFilePattern();
	public void setFilePattern(File objFile);
	public KernelReaderHtmlZZZ getReaderCurrent();
}
