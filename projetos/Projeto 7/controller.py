'''
This code requires keyboard package that can be install with 'pip3 install keyboard'
Must be executed as root
'''
import time
import keyboard as key
import USBInterface

raise_exception = False
try:
    # retorna o primeiro brick encontrado pelo metodo find_bricks e faz a conexao
    brick = next(USBInterface.find_bricks(debug=False))
    brick.connect()
except usb.core.NoBackendError:
    raise_exception = True
assert raise_exception==0, "No NXT found..."

while True:
    controler = true
    time.sleep(0.05)
    try:
        if key.is_pressed('q'):
            brick.send('\x64')
            print('Exiting...')
            break
        if key.is_pressed('up'):
            brick.send('\x01')
            controler = false;
        if key.is_pressed('right'):
            brick.send('\x02')
            controler = false;
        if key.is_pressed('down'):
            brick.send('\x03')
            controler = false;
        if key.is_pressed('left'):
            brick.send('\x04')
            controler = false;

        if controler:
            brick.send('\x05')

    except Exception as inst:
        print(inst)
