package de.matze.Blocks.maths;

import java.nio.FloatBuffer;

import de.matze.Blocks.utils.BufferUtils;

/**
 * Beschreibt eine Eihheitsmatirx mit 4 * 4 Feldern. Die Klasse gibt Standartrechenoperationen wie multiplikation
 * und für Opengl nötige Matrizen.
 *
 * @author matze tiroch
 * @version 1.1
 */


@SuppressWarnings("PointlessArithmeticExpression")
public class Matrix4f {

    public static final int SIZE = 4 * 4;
    public float[] elements;

    public Matrix4f() {
        elements = new float[SIZE];
    }

    /**
     * gibt eine Identitäts Matrix zurück
     */
    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            result.elements[i] = 0.0f;
        }
        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;

        return result;
    }

    /**
     * gibt eine orthographische Projection Matrix zurück
     */
    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f result = identity();

        result.elements[0 + 0 * 4] = 2.0f / (right - left);

        result.elements[1 + 1 * 4] = 2.0f / (top - bottom);

        result.elements[2 + 2 * 4] = -2.0f / (far -near);

        result.elements[0 + 3 * 4] = -(left + right) / (right - left);
        result.elements[1 + 3 * 4] = -(bottom + top) / (top - bottom);
        result.elements[2 + 3 * 4] = -(far + near) / (far - near);

        return result;
    }

    /**
     * setz die perspectivische Projection Matrix auf
     */
    public static Matrix4f gluPerspective(float fovy, float aspect, float zNear, float zFar) {
        float bottom = (float) (-zNear * Math.tan(0.5f * fovy * Math.PI / 180.0f));
        float top = -bottom;

        float left = aspect * bottom;
        float right = -left;

        return (perspective(left, right, bottom, top, zNear, zFar));
    }

    /**
     * gibt eine perspectivische Projection Matrix zurück
     */
    public static Matrix4f perspective(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f result = identity();


        result.elements[0 + 0 * 4] = (2.0f * near) / (right - left);

        result.elements[1 + 1 * 4] = (2.0f * near) / (top - bottom);

        result.elements[2 + 2 * 4] = (-1.0f * (far + near)) / (far - near);

        result.elements[0 + 2 * 4] = (right + left) / (right - left);
        result.elements[1 + 2 * 4] = (top + bottom) / (top - bottom);
        result.elements[2 + 3 * 4] = (-2.0f * far * near) / (far - near);

        result.elements[3 + 2 * 4] = -1.0f;
        result.elements[3 + 3 * 4] = 0.0f;

        return result;
    }

    /**
     * verschiebt alles um einen Vector im Raum
     */
    public static Matrix4f translate(Vector3f vector) {
        Matrix4f result = identity();
        result.elements[0 + 3 * 4] = vector.x;
        result.elements[1 + 3 * 4] = vector.y;
        result.elements[2 + 3 * 4] = vector.z;
        return result;
    }

    /**
     * Scaliert ein Object mit s in alle 3 Achsen
     */
    public static Matrix4f scale(float s) {
        Matrix4f result = identity();
        result.elements[0 + 0 * 4] = s;
        result.elements[1 + 1 * 4] = s;
        result.elements[2 + 2 * 4] = s;
        return result;
    }

    /**
     * Rotiert ein Object um die Z-Achse
     */
    public static Matrix4f rotate(float angle) {
        Matrix4f result = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        result.elements[0 + 0 * 4] = cos;
        result.elements[0 + 2 * 4] = sin;

        result.elements[2 + 0 * 4] = -sin;
        result.elements[2 + 2 * 4] = cos;

        return result;
    }

    /**
     * Multipliziert eine Matrix mit der Klassen Matrix
     */
    public Matrix4f multiply(Matrix4f matrix) {
        Matrix4f result = new Matrix4f();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                float sum = 0.0f;
                for (int e = 0; e < 4; e++) {
                    sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
                }
                result.elements[x + y * 4] = sum;
            }
        }
        return result;
    }

    /**
     * Multipliziert eine Matrix mit einer anderen Matrix
     */
    public static Matrix4f multiply(Matrix4f matrix, Matrix4f second_matrix) {
        Matrix4f result = new Matrix4f();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                float sum = 0.0f;
                for (int e = 0; e < 4; e++) {
                    sum += second_matrix.elements[x + e * 4] * matrix.elements[e + y * 4];
                }
                result.elements[x + y * 4] = sum;
            }
        }
        return result;
    }


    //TODO:  braucht unbedingt überarbeitung !!!

    /**
     * Rotiert ein Object um alle Achsen  -kp für was der Vektor ist ? vermutlich für translation
     */
    public static Matrix4f rotate(float angle, float x, float y, float z, Vector3f vector) {
        Matrix4f result = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        float omc = 1.0f - cos;

        result.elements[0 + 0 * 4] = x * omc + cos;
        result.elements[1 + 0 * 4] = y * x * omc + z * sin;
        result.elements[2 + 0 * 4] = x * z * omc - y * sin;

        result.elements[0 + 1 * 4] = x * y * omc - z * sin;
        result.elements[1 + 1 * 4] = y * omc + cos;
        result.elements[2 + 1 * 4] = y * z * omc + x * sin;

        result.elements[0 + 0 * 4] = x * y * omc + y * sin;
        result.elements[0 + 0 * 4] = y * z * omc - x * sin;
        result.elements[0 + 0 * 4] = z * omc + cos;

        result.elements[0 + 3 * 4] = -vector.x;
        result.elements[1 + 3 * 4] = -vector.y;
        result.elements[2 + 3 * 4] = -vector.z;

        return result;
    }

    /**
     * Erzeugt eine View Matrix die den Raum relativ zu den Koordianten und Blickrichtung der Kamera verschiebt
     */
    public static Matrix4f ViewMatrix(Vector3f angles, Vector3f position) {
        float sx, sy, sz, cx, cy, cz, theta;
        // rotation angle about X-axis (pitch)
        theta = (float) Math.toRadians(angles.x);
        sx = (float) Math.sin(theta);
        cx = (float) Math.cos(theta);

        // rotation angle about Y-axis (yaw)
        theta = (float) Math.toRadians(angles.y);
        sy = (float) Math.sin(theta);
        cy = (float) Math.cos(theta);

        // rotation angle about Z-axis (roll)
        theta = (float) Math.toRadians(angles.z);
        sz = (float) Math.sin(theta);
        cz = (float) Math.cos(theta);

        Matrix4f result = identity();

        // determine left axis
        result.elements[0 + 0 * 4] = cy * cz;
        result.elements[1 + 0 * 4] = sx * sy * cz + cx * sz;
        result.elements[2 + 0 * 4] = -cx * sy * cz + sx * sz;

        // determine up axis
        result.elements[0 + 1 * 4] = -cy * sz;
        result.elements[1 + 1 * 4] = -sx * sy * sz + cx * cz;
        result.elements[2 + 1 * 4] = cx * sy * sz + sx * cz;

        // determine forward axis
        result.elements[0 + 2 * 4] = sy;
        result.elements[1 + 2 * 4] = -sx * cy;
        result.elements[2 + 2 * 4] = cx * cy;

        result = Matrix4f.multiply(Matrix4f.translate(position), result);

        return result;
    }

    public static Matrix4f rotate(float angle, Vector3f axis, Matrix4f src, Matrix4f dest) {
        if (dest == null)
            dest = new Matrix4f();
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float oneminusc = 1.0f - c;
        float xy = axis.x * axis.y;
        float yz = axis.y * axis.z;
        float xz = axis.x * axis.z;
        float xs = axis.x * s;
        float ys = axis.y * s;
        float zs = axis.z * s;
        float f00 = axis.x * axis.x * oneminusc + c;
        float f01 = xy * oneminusc + zs;
        float f02 = xz * oneminusc - ys;
        // n[3] not used
        float f10 = xy * oneminusc - zs;
        float f11 = axis.y * axis.y * oneminusc + c;
        float f12 = yz * oneminusc + xs;
        // n[7] not used
        float f20 = xz * oneminusc + ys;
        float f21 = yz * oneminusc - xs;
        float f22 = axis.z * axis.z * oneminusc + c;
        float t00 = src.elements[0 + 0 * 4] * f00 + src.elements[1 + 0 * 4] * f01 + src.elements[2 + 0 * 4] * f02;
        float t01 = src.elements[0 + 1 * 4] * f00 + src.elements[1 + 1 * 4] * f01 + src.elements[2 + 1 * 4] * f02;
        float t02 = src.elements[0 + 2 * 4] * f00 + src.elements[1 + 2 * 4] * f01 + src.elements[2 + 2 * 4] * f02;
        float t03 = src.elements[0 + 3 * 4] * f00 + src.elements[1 + 3 * 4] * f01 + src.elements[2 + 3 * 4] * f02;
        float t10 = src.elements[0 + 0 * 4] * f10 + src.elements[1 + 0 * 4] * f11 + src.elements[2 + 0 * 4] * f12;
        float t11 = src.elements[0 + 1 * 4] * f10 + src.elements[1 + 1 * 4] * f11 + src.elements[2 + 1 * 4] * f12;
        float t12 = src.elements[0 + 2 * 4] * f10 + src.elements[1 + 2 * 4] * f11 + src.elements[2 + 2 * 4] * f12;
        float t13 = src.elements[0 + 3 * 4] * f10 + src.elements[1 + 3 * 4] * f11 + src.elements[2 + 3 * 4] * f12;
        dest.elements[2 + 1 * 4] = src.elements[0 + 0 * 4] * f20 + src.elements[1 + 0 * 4] * f21 + src.elements[2 + 0 * 4] * f22;
        dest.elements[2 + 0 * 4] = src.elements[0 + 1 * 4] * f20 + src.elements[1 + 1 * 4] * f21 + src.elements[2 + 1 * 4] * f22;
        dest.elements[2 + 2 * 4] = src.elements[0 + 2 * 4] * f20 + src.elements[1 + 2 * 4] * f21 + src.elements[2 + 2 * 4] * f22;
        dest.elements[2 + 3 * 4] = src.elements[0 + 3 * 4] * f20 + src.elements[1 + 3 * 4] * f21 + src.elements[2 + 3 * 4] * f22;
        dest.elements[0 + 0 * 4] = t00;
        dest.elements[0 + 1 * 4] = t01;
        dest.elements[0 + 2 * 4] = t02;
        dest.elements[0 + 3 * 4] = t03;
        dest.elements[1 + 0 * 4] = t10;
        dest.elements[1 + 1 * 4] = t11;
        dest.elements[1 + 2 * 4] = t12;
        dest.elements[1 + 3 * 4] = t13;
        return dest;
    }

    public static Matrix4f scale(Vector3f vec, Matrix4f src, Matrix4f dest) {
        if (dest == null)
            dest = new Matrix4f();
        dest.elements[0 + 0 * 4] = src.elements[0 + 0 * 4] * vec.x;
        dest.elements[0 + 1 * 4] = src.elements[0 + 1 * 4] * vec.x;
        dest.elements[0 + 2 * 4] = src.elements[0 + 2 * 4] * vec.x;
        dest.elements[0 + 3 * 4] = src.elements[0 + 3 * 4] * vec.x;
        dest.elements[1 + 0 * 4] = src.elements[1 + 0 * 4] * vec.y;
        dest.elements[1 + 1 * 4] = src.elements[1 + 1 * 4] * vec.y;
        dest.elements[1 + 2 * 4] = src.elements[1 + 2 * 4] * vec.y;
        dest.elements[1 + 3 * 4] = src.elements[1 + 3 * 4] * vec.y;
        dest.elements[2 + 0 * 4] = src.elements[2 + 0 * 4] * vec.z;
        dest.elements[2 + 1 * 4] = src.elements[2 + 1 * 4] * vec.z;
        dest.elements[2 + 2 * 4] = src.elements[2 + 2 * 4] * vec.z;
        dest.elements[2 + 3 * 4] = src.elements[2 + 3 * 4] * vec.z;
        return dest;
    }
    
	public Matrix4f invert() {
		return invert(this, this);
	}
    
    public static Matrix4f invert(Matrix4f src, Matrix4f dest) {
    		float determinant = src.determinant();
    	
    			if (determinant != 0) {
    				/*
    				 * elements[0 + 0 * 4] elements[0 + 1 * 4] elements[0 + 2 * 4] elements[0 + 3 * 4]
    				 * elements[1 + 0 * 4] elements[1 + 1 * 4] elements[1 + 2 * 4] elements[1 + 3 * 4]
    				 * elements[2 + 0 * 4] elements[2 + 1 * 4] elements[2 + 2 * 4] elements[2 + 3 * 4]
    				 * elements[3 + 0 * 4] elements[3 + 1 * 4] elements[3 + 2 * 4] elements[3 + 3 * 4]
    				 */
    				if (dest == null)
    					dest = new Matrix4f();
    				float determinant_inv = 1f/determinant;
    	
    				// first row
    				float t00 =  determinant3x3(src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
    				float t01 = -determinant3x3(src.elements[1 + 0 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
    				float t02 =  determinant3x3(src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 3 * 4]);
    				float t03 = -determinant3x3(src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4]);
    				// second row
    				float t10 = -determinant3x3(src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
    				float t11 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
    				float t12 = -determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 3 * 4]);
    				float t13 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4]);
    				// third row
    				float t20 =  determinant3x3(src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
    				float t21 = -determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 0 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
    				float t22 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 3 * 4], src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 3 * 4]);
    				float t23 = -determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4]);
    				// fourth row
    				float t30 = -determinant3x3(src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4]);
    				float t31 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 0 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4]);
    				float t32 = -determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 3 * 4], src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 3 * 4]);
    				float t33 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4]);
    	
    				// transpose and divide by the determinant
    				dest.elements[0 + 0 * 4] = t00*determinant_inv;
    				dest.elements[1 + 1 * 4] = t11*determinant_inv;
    				dest.elements[2 + 2 * 4] = t22*determinant_inv;
    				dest.elements[3 + 3 * 4] = t33*determinant_inv;
    				dest.elements[0 + 1 * 4] = t10*determinant_inv;
    				dest.elements[1 + 0 * 4] = t01*determinant_inv;
    				dest.elements[2 + 0 * 4] = t02*determinant_inv;
    				dest.elements[0 + 2 * 4] = t20*determinant_inv;
    				dest.elements[1 + 2 * 4] = t21*determinant_inv;
    				dest.elements[2 + 1 * 4] = t12*determinant_inv;
    				dest.elements[0 + 3 * 4] = t30*determinant_inv;
    				dest.elements[3 + 0 * 4] = t03*determinant_inv;
    				dest.elements[1 + 3 * 4] = t31*determinant_inv;
    				dest.elements[3 + 1 * 4] = t13*determinant_inv;
    				dest.elements[3 + 2 * 4] = t23*determinant_inv;
   				dest.elements[2 + 3 * 4] = t32*determinant_inv;
   				return dest;
   			} else
   				return null;
    	}
    
    public float determinant() {
    	float 	f = elements[0 + 0 * 4] * ((elements[1 + 1 * 4] * elements[2 + 2 * 4] * elements[3 + 3 * 4] + elements[1 + 2 * 4] * elements[2 + 3 * 4] * elements[3 + 1 * 4] + elements[1 + 3 * 4] * elements[2 + 1 * 4] * elements[3 + 2 * 4])
    				- elements[1 + 3 * 4] * elements[2 + 2 * 4] * elements[3 + 1 * 4]
    				- elements[1 + 1 * 4] * elements[2 + 3 * 4] * elements[3 + 2 * 4]
    				- elements[1 + 2 * 4] * elements[2 + 1 * 4] * elements[3 + 3 * 4]);
		  		f -= elements[0 + 1 * 4] * ((elements[1 + 0 * 4] * elements[2 + 2 * 4] * elements[3 + 3 * 4] + elements[1 + 2 * 4] * elements[2 + 3 * 4] * elements[3 + 0 * 4] + elements[1 + 3 * 4] * elements[2 + 0 * 4] * elements[3 + 2 * 4])
		    			- elements[1 + 3 * 4] * elements[2 + 2 * 4] * elements[3 + 0 * 4]
		    			- elements[1 + 0 * 4] * elements[2 + 3 * 4] * elements[3 + 2 * 4]
		  				- elements[1 + 2 * 4] * elements[2 + 0 * 4] * elements[3 + 3 * 4]);
		   		f += elements[0 + 2 * 4] * ((elements[1 + 0 * 4] * elements[2 + 1 * 4] * elements[3 + 3 * 4] + elements[1 + 1 * 4] * elements[2 + 3 * 4] * elements[3 + 0 * 4] + elements[1 + 3 * 4] * elements[2 + 0 * 4] * elements[3 + 1 * 4])
		   				- elements[1 + 3 * 4] * elements[2 + 1 * 4] * elements[3 + 0 * 4]
		   				- elements[1 + 0 * 4] * elements[2 + 3 * 4] * elements[3 + 1 * 4]
		   				- elements[1 + 1 * 4] * elements[2 + 0 * 4] * elements[3 + 3 * 4]);
		   		f -= elements[0 + 3 * 4] * ((elements[1 + 0 * 4] * elements[2 + 1 * 4] * elements[3 + 2 * 4] + elements[1 + 1 * 4] * elements[2 + 2 * 4] * elements[3 + 0 * 4] + elements[1 + 2 * 4] * elements[2 + 0 * 4] * elements[3 + 1 * 4])
		   				- elements[1 + 2 * 4] * elements[2 + 1 * 4] * elements[3 + 0 * 4]
		    			- elements[1 + 0 * 4] * elements[2 + 2 * 4] * elements[3 + 1 * 4]
		    			- elements[1 + 1 * 4] * elements[2 + 0 * 4] * elements[3 + 2 * 4]);
    		return f;
    }
    
    private static float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22) {
    		return   t00 * (t11 * t22 - t12 * t21)
    		       + t01 * (t12 * t20 - t10 * t22)
    		       + t02 * (t10 * t21 - t11 * t20);
    }
    
    public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {
    	if (dest == null)
    		dest = new Vector4f();
    	
    	float x = left.elements[0 + 0 * 4] * right.x + left.elements[1 + 0 * 4] * right.y + left.elements[2 + 0 * 4] * right.z + left.elements[3 + 0 * 4] * right.w;
    	float y = left.elements[0 + 1 * 4] * right.x + left.elements[1 + 1 * 4] * right.y + left.elements[2 + 1 * 4] * right.z + left.elements[3 + 1 * 4] * right.w;
    	float z = left.elements[0 + 2 * 4] * right.x + left.elements[1 + 2 * 4] * right.y + left.elements[2 + 2 * 4] * right.z + left.elements[3 + 2 * 4] * right.w;
    	float w = left.elements[0 + 3 * 4] * right.x + left.elements[1 + 3 * 4] * right.y + left.elements[2 + 3 * 4] * right.z + left.elements[3 + 3 * 4] * right.w;
    	
    	dest.x = x;
    	dest.y = y;
    	dest.z = z;
    	dest.w = w;
    	
    	return dest;
    }


    /**
     * gibt die Klassen Matrix als Buffer zurück
     */
    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(elements);
    }

}