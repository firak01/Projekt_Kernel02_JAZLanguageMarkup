package basic.zKernel.markup.content;

import basic.zBasic.ExceptionZZZ;

public interface IKernelContentComputableZZZ {
	/**
	 * this method must be overwritten by a customized contentStore
	 * @return boolean true/false indicating the success of the method
	 * @throws ExceptionZZZ
	 */
	abstract public boolean compute() throws ExceptionZZZ;
}
