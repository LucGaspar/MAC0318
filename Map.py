import matplotlib.pyplot as plt
import numpy as np
from scipy.ndimage.morphology import binary_dilation 
import math

class Map(object):
	def __init__(self, width, height, scale = 1):
		self.map    = np.zeros(shape = (width, height))
		self.path   = np.zeros(shape = (width, height))
		self.width  = width
		self.height = height
		self.scale  = scale

	def draw_line(self, x0, y0, x1, y1):
		if (x1.is_integer()):
			x1 = x1 + self.scale / 12
		if (y1.is_integer()):
			y1 = y1 + self.scale / 12
		self.discretize(x0, y0, x1, y1)

	def print(self):
		plt.matshow(self.map)
		plt.show()
			
	def dilation(self, width, height, scale = 1):
		self.map = binary_dilation(self.map, np.ones(shape = (int (width / scale), int (height / scale))))

	def mid(self, x0, y0, x1, y1):
		return (x1 + x0) / 2.0 , (y1 + y0) / 2.0 

	def distance(self, x0, y0, x1, y1):
		return math.sqrt( (x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0) )

	def discretize(self, x0, y0, x1, y1):
		x, y = self.mid(x0, y0, x1, y1)
		if (self.distance(x0, y0, x1, y1) < self.scale / 10):
			return
		print("x0: ", x0, "\ny0: ", y0, "\nx1")
		self.map[int(x)][int(y)] = 1
		self.discretize(x0, y0, x, y)
		self.discretize(x, y, x1, y1)

	def step(self, A, n, x0, y0, x1, y1):
		if (x0 == x1 and y0 == y1):
			return n

		self.push(A, x0 + 1, y0    , n+1)
		self.push(A, x0 + 1, y0 + 1, n+1)
		self.push(A, x0	   , y0 + 1, n+1)
		self.push(A, x0 - 1, y0 + 1, n+1)
		self.push(A, x0 - 1, y0    , n+1)
		self.push(A, x0 - 1, y0 - 1, n+1)
		self.push(A, x0	   , y0 - 1, n+1)
		self.push(A, x0 + 1, y0 - 1, n+1)

		v = A.pop(0)

		return self.step(A, v[2], v[0], v[1], x1, y1)

	def push(self, A, x0, y0, n):

		if (x0 < self.width and y0 < self.height and x0 >= 0 and y0 >= 0):
			if (self.map[x0, y0] == 0 and self.path[x0][y0] == 0):
				A.append([x0, y0, n])
				self.path[x0][y0] = n

	def step_back(self, B, n, x0, y0):
		if (n == 0):
			return 1

		for i in [-1,0,1]:
			for j in [-1, 0, 1]:
				if (x0 + i < 0 or x0 + i >= self.width or y0 + j < 0 or y0 + j >= self.height or self.map[x0 + i][y0 + j] == -2):
					continue
				if (self.path[x0 + i][y0 + j] == n - 1): 
					B.append([x0 + i, y0 + j])
					return self.step_back(B, n - 1, x0 + i, y0 + j)


	def depth_find(self, x0, y0, x1, y1):
		if (self.map[x1][y1] == 1):
			return 0
		
		self.path = np.zeros(shape = (self.width, self.height))
		self.map = self.map * -2
		A = []
		B = []
		n = self.step(A, 0, x0, y0, x1, y1)
		if (n != 0):
			self.step_back(B, n, x1, y1)
		
		self.map[x1][y1] = 12
		self.map[x0][y0] = 12
		for v in B:
			self.map[v[0]][v[1]] = 5



map = Map(10,10)
map.draw_line(1, 2, 7.0, 8.0)
map.dilation(2, 2)
map.depth_find(6, 2, 1, 8)

map.print()