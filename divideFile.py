import os

# folderImg = "real_chars/"
folderImg = "real_char2/"
labels = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']

os.system("mdkir -p labels")
for label in labels:
    os.system("mkdir -p labels/"+ label)

for imgFile in os.listdir(folderImg):
    name, ext = os.path.splitext(imgFile)
    nameEnd = name[-1:]
    command = "mv "+ folderImg+ imgFile + " labels/"+nameEnd
    os.system(command)

    print(command)