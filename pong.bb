SeedRnd(MilliSecs())
timebegin = MilliSecs()
timeElapsed = MilliSecs() 
playTo = 5

;Image Loading
bg = LoadImage("bg.bmp")
bgWin = LoadImage("backgroundWinner.bmp")
paddle1= LoadImage("playerone.bmp")
paddle2 = LoadImage("playertwo.bmp")
ball = LoadImage("ball.bmp")
blip = LoadSound("blip.wav")
blip2 = LoadSound("blip2.wav")
score = LoadSound("score.wav")
victory = LoadSound("victory.wav")

Graphics 800,600,32,2
SetBuffer BackBuffer()

;Variables
p1y = 200
p2y = 200

bx = 375
by = 275
dx = 4
dy = 4

;Loading Screen
While KeyDown(28)=0
	Cls 
	If (KeyHit(1)) Then End 
	Text 300,200,"Welcome to pong!"
	Text 300,300,"Score Selected: " + Str(playTo)
	Text 220,350,"Use UP and DOWN to change goal score"
	Text 300,375,"Press ENTER to play!"
	If(KeyHit(200)=1) And playTo >= 1 Then 
		playTo = playTo + 1 
	EndIf 
	If(KeyHit(208)) And playTo >1 Then 
		playTo = playTo - 1 
	EndIf 
	Flip 
Wend


playerOneScore = 0 
playerTwoScore = 0 
While KeyDown(1) = 0 
	
	DrawImage bg,0,0
	DrawImage paddle1,0,p1y
	DrawImage paddle2,775,p2y
	DrawImage ball,bx,by
	Text 200,50,"Player One: " + Str(playerOneScore)
	Text 500,50,"Player Two: " + Str(playerTwoScore)
	Text 375,50,"Speed: " + Str(dx)
	Text 350,500,"Time Elapsed: " + Str((MilliSecs() - timeElapsed) / 1000)
	;ball movement code
	bx = bx + dx
	by = by + dy
	
	
	;Timecheck
	If(MilliSecs() - timebegin) >= 5000
		If dx < 0 Then 
			dx = dx - 1 
		ElseIf dx > 0 Then
			dx = dx + 1
		EndIf 
		If dy < 0 Then
			dy = dy - 1 
		ElseIf dy > 0 Then
			dy = dy + 1 
		EndIf 
		timebegin = MilliSecs()
	EndIf 
	
	;ball collision code 
	If by <= 0 Or by >= 575 Then
		dy = - dy
		PlaySound blip2
	EndIf 
	If bx <= 0 Then 
		playerTwoScore = playerTwoScore + 1
		If dx < 0 Then 
			dx = -4
		ElseIf dx > 0 Then
			dx = 4
		EndIf 
		If dy < 0 Then
			dy = -4
		ElseIf dy > 0 Then
			dy = 4
		EndIf 
		timebegin = MilliSecs()
		If playerTwoScore >= playTop Then
			bg = bgWin
			DrawImage bg,0,0
			Text 300,300,"Congrats, Player Two!"
			PlaySound victory
			Flip
			Delay 5000 
			End
		EndIf 
		bx = 375
		by = 275
		PlaySound score
		dx = -dx
		DrawImage ball,bx,by
		Flip
		Delay 2000
	EndIf 
	If bx >= 775 Then
		playerOneScore = playerOneScore + 1 
		If dx < 0 Then 
			dx = -4
		ElseIf dx > 0 Then
			dx = 4
		EndIf 
		If dy < 0 Then
			dy = -4
		ElseIf dy > 0 Then
			dy = 4
		EndIf 
		timebegin = MilliSecs()
		If playerOneScore >= playTo Then
			bg = bgWin
			DrawImage bg,0,0
			Text 300,300,"Congrats, Player One!"
			PlaySound victory
			Flip
			Delay 5000
			End 
		EndIf 
		bx = 375
		by = 275
		PlaySound score
		dx = -dx
		DrawImage ball,bx,by
		Flip
		Delay 2000
	EndIf 
	
	;Paddle Collision Code
	If ImagesCollide(ball,bx,by,0,paddle1,0,p1y,0) Then
		dx = -dx 
		PlaySound blip
	EndIf
	If ImagesCollide(ball,bx,by,0,paddle2,775,p2y,0) Then
		dx = -dx
		PlaySound blip
	EndIf
	;Keychecks!
	If KeyDown(30) And p1y >= 5 Then p1y = p1y - 5
	If KeyDown(44) And p1y <= 500 Then p1y = p1y + 5
	If KeyDown(200) And p2y >= 5 Then p2y = p2y - 5
	If KeyDown(208) And p2y <= 500 Then p2y = p2y + 5
	
	Flip ;refresh the screen
Wend 