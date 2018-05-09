import os
import argparse
import numpy as np
from util import get_image_and_command, get_image, get_flat_shape
from util import load_dataset, save_dataset

def main():
	data, labels = load_dataset("binarized/0b_120_160_data.npy",
	 "binarized/0b_120_160_labels.npy")

	sumL = np.zeros(shape=(120,160))
	sumR = np.zeros(shape=(120,160))
	countL = 0
	countR = 0

	for i in range(data.shape[0]):
		frame, cmd = get_image_and_command(data[i],
                                           labels[i],
                                           120,
                                           160,
                                           1)
		if (cmd == "right"):
			sumR   = sumR   + frame
			countR = countR + 1
		else:
			sumL   = sumL + frame
			countL = countL + 1

		print(cmd)

	np.divide(sumL, countL, sumL)
	np.divide(sumR, countR, sumR)
	
	result = maximum(sumL, sumR)
	x = "Posição %d, %d\nSendo %d preto" % (result[0], result[1], result[2])
	print(x)

# x, y, qual é maior
def maximum(sum0, sum1):
	result = []
	result.append(0)
	result.append(1)
	greater = 0
	for i in range(120):
		for j in range(160):
			delta = abs(sum0[i][j] - sum1[i][j])
			if (delta > greater):
				greater = delta
				result[0] = i
				result[1] = j

	if (sum0[result[0]][result[1]] > sum1[result[0]][result[1]]):
		result.append(0)
	else:
		result.append(1)

	return result

main()
