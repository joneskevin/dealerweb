/*

jQuery Speedometer v1.0.3
by Jacob King
http://www.jacob-king.com/

Tested on IE7 and Firefox 3.6.3 with jQuery 1.4.2
***Requires jquery.jqcanvas-modified.js and excanvas-modified.js***



Usage: 
	$([selector]).speedometer([options object]);

Options:
	percentage: (float/int, default: 0) 
		Value to display on speedometer and digital readout. Can also be specified as the selector's innerHTML.
	scale: (float/int, default 100)
		The value considered to be 100% on the speedometer.
	limit: (float/int, default true)
		Specifies that the speedometer will "break" if the value is out of bounds.
	minimum: (float/int, default 0)
		The lowest value the needle can go without the glass cracking.
	maximum: (float/int, default 100)
		The highest value the needle can go without the glass cracking.
	animate: (boolean, default: true)
		Specifies that the speedometer needle will animate from current value to intended value.	
	suffix: (string, default ' %')
		A unit/string to display after the digital readout's value. Set to '' for none.
	
	thisCss: Default settings object for speedometer. Modifying this is not supported.
	
	digitalCss: Default settings object for digital readout. Modifying this is not supported.
	
*/

( function( $ ){

	$.fn.speedometer = function( options ){
	
		/* A tad bit speedier, plus avoids possible confusion with other $(this) references. */
		var $this = $( this );
		

		
		/* handle multiple selectors */
		if ( $this.length > 1 ) {
			$this.each( function(){
				$( this ).speedometer( options );
				
			});
			return $this;
		}	

		var def = {
			/* If not specified, look in selector's innerHTML before defaulting to zero. */
			percentage : $.trim( $this.html() ) || 0,
			scale: 100,
			limit: true,
			minimum: 0,
			maximum: 100,
			suffix: '',
			animate:true,
			backgroundImg:'/js/jquery/speedometer/background_100.jpg',
			thisCss: {
				position: 'relative', /* Very important to align needle with gague. */
				width: '105px',
				height: '90px',
				padding: '0px',
				border: '0px',
				fontFamily: 'Arial',
				fontWeight: '900',
				backgroundImage : "url('/js/jquery/speedometer/background_100.jpg')"

		
			},
			digitalCss: {
				backgroundColor:'black',
				borderColor:'#555555 #999999 #999999 #555555',
				borderStyle:'solid',
				borderWidth:'2px',
				color:'white',
				fontSize:'10px',
				height:'10px',
				left:'35px',
				padding:'1px',
				position:'absolute',
				textAlign:'center',
				top:'32px',
				width:'30px',
				zIndex:'10',
				lineHeight:'10px',
				overflow:'hidden'
			}
		}
		

		
		$this.html( '' );
		
		$this.css( def.thisCss );
		
		$.extend( def, options );
		
		var showPercentage = def.percentage;
		if ( def.percentage > def.scale ){
			def.percentage = def.scale;
		}
		
		$this.css( 'backgroundImage' , 'url(' + def.backgroundImg +')' );
		
		/* out of range */
		if ( def.limit && ( def.percentage > def.maximum || def.percentage < def.minimum ) ) {
			/* the glass cracks */
			$this.css( 'backgroundImage' , 'url("/js/jquery/speedometer/background-broken.jpg")' );
		} else {
		
			



		
			/* call modified jqcanvas file to generate needle line */
			$this.jqcanvas ( function ( canvas, width, height ) {
		
				var ctx = canvas.getContext ( "2d" ),
				lingrad, thisWidth;
	
				ctx.lineWidth = 2;
				ctx.strokeStyle = "rgb(255,0,0)";
				
				/* point of origin for drawing AND canvas rotation (lines up with middle of the black circle on the image) */
				ctx.translate( 52, 52 );	
							
				ctx.save(); //remember linewidth, strokestyle, and translate
				
				function animate(){		
		
					ctx.restore(); //reset ctx.rotate to properly draw clearRect
					ctx.save();	//remember this default state again
		
					ctx.clearRect( -105, -105, 300, 300 ); //erase the canvas
		
					
					
		
		
					/* rotate based on percentage. */
					ctx.rotate( i * Math.PI / def.scale );
		
		
		
					/* draw the needle */
					ctx.beginPath();
					ctx.moveTo( -30,0 );
					ctx.lineTo( 10,0 );
					ctx.stroke();
					
					/* internally remember current needle value */
					$this.data('currentPercentage',i);
					
					if ( i != def.percentage ) {
					
						//properly handle fractions
						i += Math.abs( def.percentage - i ) < 1 ? def.percentage - i : def.increment;
		
						setTimeout(function(){
							animate()
						},20);
					}
				}				
				
				/* Are we animating or just displaying the percentage? */
				if (def.animate) {
					var i = parseInt( $this.data('currentPercentage') ) || 0;
					def.increment = ( i < def.percentage ) ? 1 : -1;
				} else {
					var i = ( def.percentage );
				}
				
				
				animate();
				
				
				
			
			}, { verifySize: false, customClassName: '' } );


		
		}
		
		/* digital percentage displayed in middle of image */

		var digitalGauge = $( '<div></div>' );
		$this.append( digitalGauge );
		digitalGauge.css( def.digitalCss );
		digitalGauge.text( showPercentage + def.suffix );
		

		
		return $this;

	}

	

})( jQuery )