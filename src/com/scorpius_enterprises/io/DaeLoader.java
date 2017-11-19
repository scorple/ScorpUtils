package com.scorpius_enterprises.io;

import com.scorpius_enterprises.log.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * {@link DaeLoader com.scorpius_enterprises.io.DaeLoader}
 *
 * @author Scorple
 * @since 2017-03-16
 */
public class DaeLoader
{
    public static final int POSITION_SIZE         = 3;
    public static final int TEXTURE_POSITION_SIZE = 2;
    public static final int NORMAL_VECTOR_SIZE    = 3;

    private static final String TAG_MESH = "mesh";

    private static ArrayList<Float>   posCoords;
    private static ArrayList<Float>   texCoords;
    private static ArrayList<Float>   normalVecComps;
    private static ArrayList<Integer> indices;

    private static Float[] texCoordsInVertexOrder      = null;
    private static Float[] normalVecCompsInVertexOrder = null;

    private static HashMap<Integer, float[]> texCoordsByPosIndex;
    private static HashMap<Integer, float[]> normalVecCompsByPosIndex;

    public static void loadIBO(final String fileName,
                               final int[] numIndices,
                               final float[][] posCoordArray,
                               final float[][] texCoordArray,
                               final float[][] normalVecCompArray,
                               final int[][] vertexIndexArray)
    {
        posCoords = new ArrayList<>();
        texCoords = new ArrayList<>();
        normalVecComps = new ArrayList<>();
        indices = new ArrayList<>();

        texCoordsByPosIndex = new HashMap<>();
        normalVecCompsByPosIndex = new HashMap<>();

        SAXParserFactory factory = SAXParserFactory.newInstance();

        File file = new File(DaeLoader.class.getClass()
                                            .getResource(fileName)
                                            .getFile());

        try
        {
            SAXParser parser = factory.newSAXParser();
            parser.parse(file,
                         new DAEElementHandler());
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXTerminatorException e)
        {

        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (texCoords.size() > 0)
        {
            texCoordsInVertexOrder = new Float[texCoordsByPosIndex.size() * TEXTURE_POSITION_SIZE];
        }

        if (normalVecComps.size() > 0)
        {
            normalVecCompsInVertexOrder = new Float[normalVecCompsByPosIndex.size()
                                                    * NORMAL_VECTOR_SIZE];
        }

        try
        {
            if (texCoordsInVertexOrder != null)
            {
                Float[] finalTexCoordsInVertexOrder = texCoordsInVertexOrder;

                texCoordsByPosIndex.keySet()
                                   .stream()
                                   .parallel()
                                   .forEach(vertexIndex -> IntStream.range(0,
                                                                           TEXTURE_POSITION_SIZE)
                                                                    .parallel()
                                                                    .forEach(
                                                                        texCoordIndex ->
                                                                            finalTexCoordsInVertexOrder[
                                                                                vertexIndex *
                                                                                TEXTURE_POSITION_SIZE
                                                                                +
                                                                                texCoordIndex]
                                                                                =
                                                                                texCoordsByPosIndex
                                                                                    .get(
                                                                                        vertexIndex)[texCoordIndex]));
            }

            if (normalVecCompsInVertexOrder != null)
            {
                Float[] finalNormalsInVertexOrder = normalVecCompsInVertexOrder;

                normalVecCompsByPosIndex.keySet()
                                        .stream()
                                        .parallel()
                                        .forEach(vertexIndex -> IntStream.range(0,
                                                                                NORMAL_VECTOR_SIZE)
                                                                         .parallel()
                                                                         .forEach
                                                                             (normalCoordIndex ->
                                                                                      finalNormalsInVertexOrder[
                                                                                          vertexIndex
                                                                                          *
                                                                                          NORMAL_VECTOR_SIZE
                                                                                          +
                                                                                          normalCoordIndex]
                                                                                          =
                                                                                          normalVecCompsByPosIndex.get(
                                                                                              vertexIndex)[normalCoordIndex]));
            }
        }
        catch (Exception X)
        {
            Logger.logE("encountered bad index for model <" + fileName + ">, check model format");
            X.printStackTrace();
            return;
        }

        numIndices[0] = indices.size();

        //Logger.logD("" + numIndices[0]);

        if (posCoordArray != null && posCoords.size() > 0)
        {
            //Logger.logD("" + posCoords.size());

            posCoordArray[0] = new float[posCoords.size()];
            IntStream.range(0,
                            posCoordArray[0].length)
                     .parallel()
                     .forEach(i -> posCoordArray[0][i] = posCoords.get(i));
        }
        if (texCoordArray != null)
        {
            if (texCoordsInVertexOrder != null)
            {
                //Logger.logD("" + texCoordsInVertexOrder.length);

                texCoordArray[0] = new float[texCoordsInVertexOrder.length];
                Float[] finalTexCoordsInVertexOrder1 = texCoordsInVertexOrder;
                IntStream.range(0,
                                texCoordArray[0].length)
                         .parallel()
                         .forEach(i -> texCoordArray[0][i] = finalTexCoordsInVertexOrder1[i]);
            }
            else
            {
                texCoordArray[0] = null;
            }
        }
        if (normalVecCompArray != null)
        {
            if (normalVecCompsInVertexOrder != null)
            {
                //Logger.logD("" + normalVecCompsInVertexOrder.length);

                normalVecCompArray[0] = new float[normalVecCompsInVertexOrder.length];
                Float[] finalNormalsInVertexOrder1 = normalVecCompsInVertexOrder;
                IntStream.range(0,
                                normalVecCompArray[0].length)
                         .parallel()
                         .forEach(i -> normalVecCompArray[0][i] = finalNormalsInVertexOrder1[i]);
            }
            else
            {
                normalVecCompArray[0] = null;
            }
        }
        if (vertexIndexArray != null && indices.size() > 0)
        {
            //Logger.logD("" + indices.size());

            vertexIndexArray[0] = new int[indices.size()];
            IntStream.range(0,
                            vertexIndexArray[0].length)
                     .parallel()
                     .forEach(i -> vertexIndexArray[0][i] = indices.get(i));
        }
    }

    private static class DAEElementHandler
        extends DefaultHandler
    {
        private static boolean foundMesh  = false;
        private static boolean foundArray = false;

        private static boolean foundPositions = false;
        private static boolean foundNormals   = false;
        private static boolean foundMap       = false;
        private static boolean foundIndices   = false;

        @Override
        public void setDocumentLocator(Locator locator)
        {

        }

        @Override
        public void startDocument()
            throws
            SAXException
        {

        }

        @Override
        public void endDocument()
            throws
            SAXException
        {

        }

        @Override
        public void startPrefixMapping(String prefix,
                                       String uri)
            throws
            SAXException
        {

        }

        @Override
        public void endPrefixMapping(String prefix)
            throws
            SAXException
        {

        }

        @Override
        public void startElement(String uri,
                                 String localName,
                                 String qName,
                                 Attributes atts)
            throws
            SAXException
        {
            //Logger.logD(qName);

            if (!foundMesh && qName.equals("mesh"))
            {
                foundMesh = true;
            }
            else if (foundMesh && !foundArray && (qName.equals("float_array") || qName.equals("p")))
            {
                foundArray = true;
            }

            if (foundArray)
            {
                String id = atts.getValue(uri,
                                          "id");

                if (id != null)
                {
                    if (id.contains("positions"))
                    {
                        foundPositions = true;
                    }
                    else if (id.contains("normals"))
                    {
                        foundNormals = true;
                    }
                    else if (id.contains("map"))
                    {
                        foundMap = true;
                    }
                }
                else
                {
                    foundIndices = true;
                }
            }
        }

        @Override
        public void endElement(String uri,
                               String localName,
                               String qName)
            throws
            SAXException
        {
            //Logger.logD(qName);

            if (foundMesh && qName.equals("mesh"))
            {
                throw new SAXTerminatorException();
            }
        }

        @Override
        public void characters(char[] ch,
                               int start,
                               int length)
            throws
            SAXException
        {
            if (foundArray)
            {
                if (foundPositions)
                {
                    String string = new String(ch,
                                               start,
                                               length);

                    //Logger.logD(string);

                    String[] splitString = string.split(" ");

                    IntStream.range(0,
                                    splitString.length / 3)
                             .map(i -> i * 3)
                             .parallel()
                             .forEachOrdered(i ->
                                             {
                                                 posCoords.add(
                                                     Float.parseFloat(
                                                         splitString[i]));
                                                 posCoords.add(
                                                     Float.parseFloat(
                                                         splitString[
                                                             i
                                                             +
                                                             2]));
                                                 posCoords.add(
                                                     -Float.parseFloat(
                                                         splitString[
                                                             i
                                                             +
                                                             1]));
                                             });

                    //Logger.logD("" + posCoords.size());

                    foundPositions = false;
                }
                else if (foundNormals)
                {
                    String string = new String(ch,
                                               start,
                                               length);

                    //Logger.logD(string);

                    String[] splitString = string.split(" ");
                    Arrays.stream(splitString)
                          .parallel()
                          .forEachOrdered(s -> normalVecComps.add(Float.parseFloat(s)));

                    //Logger.logD("" + normalVecComps.size());

                    foundNormals = false;
                }
                else if (foundMap)
                {
                    String string = new String(ch,
                                               start,
                                               length);

                    //Logger.logD(string);

                    String[] splitString = string.split(" ");

                    IntStream.range(0,
                                    splitString.length / 2)
                             .map(i -> i * 2)
                             .parallel()
                             .forEachOrdered(i ->
                                             {
                                                 texCoords.add(
                                                     Float.parseFloat(
                                                         splitString[i]));
                                                 texCoords.add(
                                                     1.0f
                                                     -
                                                     Float.parseFloat(
                                                         splitString[
                                                             i
                                                             +
                                                             1]));
                                             });

                    //Logger.logD("" + texCoords.size());

                    foundMap = false;
                }
                else if (foundIndices)
                {
                    String string = new String(ch,
                                               start,
                                               length);

                    //Logger.logD(string);

                    String[] splitString = string.split(" ");

                    final int[] pi = new int[3];
                    final int[] ni = new int[3];
                    final int[] ti = new int[3];

                    IntStream.range(0,
                                    splitString.length / 9)
                             .map(i -> i * 9)
                             .parallel()
                             .forEachOrdered(i ->
                                             {
                                                 IntStream.range(
                                                     0,
                                                     3)
                                                          .parallel()
                                                          .forEach(
                                                              j ->
                                                              {
                                                                  pi[j] =
                                                                      Integer
                                                                          .parseInt(
                                                                              splitString[
                                                                                  i
                                                                                  +
                                                                                  j
                                                                                  *
                                                                                  3]);
                                                                  ni[j] =
                                                                      Integer
                                                                          .parseInt(
                                                                              splitString[
                                                                                  i
                                                                                  +
                                                                                  j
                                                                                  *
                                                                                  3
                                                                                  +
                                                                                  1]);
                                                                  ti[j] =
                                                                      Integer
                                                                          .parseInt(
                                                                              splitString[
                                                                                  i
                                                                                  +
                                                                                  j
                                                                                  *
                                                                                  3
                                                                                  +
                                                                                  2]);
                                                              });

                                                 IntStream.range(
                                                     0,
                                                     3)
                                                          .parallel()
                                                          .forEachOrdered(
                                                              j ->
                                                              {
                                                                  //Logger.logD(pi[j] + " " +
                                                                  // ni[j] + " " + ti[j]);

                                                                  if (indices
                                                                      .contains(
                                                                          pi[j]))
                                                                  {
                                                                      int
                                                                          nextIndexTripleStartIndex
                                                                          =
                                                                          pi[j]
                                                                          *
                                                                          POSITION_SIZE;

                                                                      IntStream
                                                                          .range(
                                                                              0,
                                                                              3)
                                                                          .parallel()
                                                                          .forEachOrdered(
                                                                              k -> posCoords
                                                                                  .add(
                                                                                      posCoords
                                                                                          .get(
                                                                                              nextIndexTripleStartIndex
                                                                                              +
                                                                                              k)));

                                                                      pi[j] =
                                                                          (posCoords
                                                                               .size() /
                                                                           3)
                                                                          -
                                                                          1;
                                                                  }

                                                                  indices
                                                                      .add(
                                                                          pi[j]);

                                                                  if (texCoords
                                                                          .size() >
                                                                      0)
                                                                  {
                                                                      texCoordsByPosIndex
                                                                          .put(
                                                                              pi[j],
                                                                              new float[]{texCoords.get(
                                                                                  ti[j]
                                                                                  *
                                                                                  TEXTURE_POSITION_SIZE),
                                                                                          texCoords.get(
                                                                                              ti[j]
                                                                                              *
                                                                                              TEXTURE_POSITION_SIZE
                                                                                              +
                                                                                              1)});
                                                                  }

                                                                  if (normalVecComps
                                                                          .size() >
                                                                      0)
                                                                  {
                                                                      normalVecCompsByPosIndex
                                                                          .put(
                                                                              pi[j],
                                                                              new float[]{
                                                                                  normalVecComps
                                                                                      .get(
                                                                                      ni[j]
                                                                                      *
                                                                                      NORMAL_VECTOR_SIZE),
                                                                                  normalVecComps
                                                                                      .get(
                                                                                      ni[j] *
                                                                                      NORMAL_VECTOR_SIZE
                                                                                      +
                                                                                      1),
                                                                                  normalVecComps
                                                                                      .get(
                                                                                      ni[j] *
                                                                                      NORMAL_VECTOR_SIZE
                                                                                      +
                                                                                      2)});
                                                                  }
                                                              });
                                             });

                    foundIndices = false;
                }
                foundArray = false;
            }
        }

        @Override
        public void ignorableWhitespace(char[] ch,
                                        int start,
                                        int length)
            throws
            SAXException
        {

        }

        @Override
        public void processingInstruction(String target,
                                          String data)
            throws
            SAXException
        {

        }

        @Override
        public void skippedEntity(String name)
            throws
            SAXException
        {

        }
    }

    private static class SAXTerminatorException
        extends SAXException
    {

    }
}
